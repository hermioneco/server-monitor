from flask import Blueprint, jsonify, request
from app import db
from app.models import Server

bp = Blueprint("main", __name__)

@bp.route("/health")
def health():
    return jsonify({"status": "ok"}), 200

@bp.route("/servers", methods=["GET", "POST"])
def servers():
    if request.method == "GET":
        all_servers = Server.query.all()
        return jsonify([s.to_dict() for s in all_servers]), 200

    data = request.get_json()
    server = Server(
        name=data["name"],
        ip=data["ip"],
        cpu=data.get("cpu", 0.0),
        ram=data.get("ram", 0.0),
        status=data.get("status", "online")
    )
    db.session.add(server)
    db.session.commit()
    return jsonify(server.to_dict()), 201

@bp.route("/metrics")
def metrics():
    all_servers = Server.query.all()
    if not all_servers:
        return jsonify({"total": 0}), 200
    avg_cpu = sum(s.cpu for s in all_servers) / len(all_servers)
    avg_ram = sum(s.ram for s in all_servers) / len(all_servers)
    return jsonify({
        "total":   len(all_servers),
        "avg_cpu": round(avg_cpu, 2),
        "avg_ram": round(avg_ram, 2),
        "online":  sum(1 for s in all_servers if s.status == "online")
    }), 200
