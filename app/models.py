from app import db
from datetime import datetime

class Server(db.Model):
    __tablename__ = "servers"

    id       = db.Column(db.Integer, primary_key=True)
    name     = db.Column(db.String(100), nullable=False, unique=True)
    ip       = db.Column(db.String(45), nullable=False)
    cpu      = db.Column(db.Float, default=0.0)   # % usage
    ram      = db.Column(db.Float, default=0.0)   # % usage
    status   = db.Column(db.String(20), default="unknown")
    updated  = db.Column(db.DateTime, default=datetime.utcnow,
                         onupdate=datetime.utcnow)

    def to_dict(self):
        return {
            "id":      self.id,
            "name":    self.name,
            "ip":      self.ip,
            "cpu":     self.cpu,
            "ram":     self.ram,
            "status":  self.status,
            "updated": self.updated.isoformat() if self.updated else None
        }
