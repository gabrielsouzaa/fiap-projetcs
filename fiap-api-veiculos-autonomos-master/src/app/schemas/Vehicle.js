import mongoose from 'mongoose';

const VehicleSchema = mongoose.Schema(
  {
    plate: {
      type: String,
      required: true,
      unique: true,
    },
    model: {
      type: String,
      required: true,
    },
    color: {
      type: String,
      required: true,
    },
    year: {
      type: Number,
      required: true,
    },
    category: {
      type: String,
      required: true,
    },
    owner: {
      type: String,
      required: true,
    },
    brand: {
      type: String,
      required: true,
    },
    available: {
      type: Boolean,
      default: true,
    },
  },
  {
    timestamps: true,
  }
);

export default mongoose.model('Vehicle', VehicleSchema);
