import mongoose from 'mongoose';
import bcrypt from 'bcryptjs';

const UserSchema = new mongoose.Schema(
  {
    name: {
      type: String,
      required: true,
    },
    email: {
      type: String,
      unique: true,
      required: true,
    },
    password: {
      type: String,
      required: true,
    },
    wallets: {
      type: Array,
      default: [],
    },
    inTravel: {
      type: Boolean,
      default: false,
    },
  },
  {
    timestamps: true,
  }
);

function encrypt(next) {
  this.password = bcrypt.hashSync(this.password, 8);
  next();
}

UserSchema.pre('save', encrypt);

export default mongoose.model('User', UserSchema);
