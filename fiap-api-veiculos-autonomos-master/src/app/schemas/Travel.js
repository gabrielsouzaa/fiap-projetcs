import mongoose from 'mongoose';

const TravelSchema = mongoose.Schema({
  finished: {
    type: Boolean,
    default: false,
  },
  user: {
    type: Object,
    required: true,
  },
  vehicle: {
    type: Object,
    required: true,
  },
  price: {
    type: Number,
    required: true,
  },
  start_point: {
    type: Number,
    required: true,
  },
  finish_point: {
    type: Number,
    required: true,
  },
  payed: {
    type: Boolean,
    default: false,
  },
});

export default mongoose.model('Travel', TravelSchema);
