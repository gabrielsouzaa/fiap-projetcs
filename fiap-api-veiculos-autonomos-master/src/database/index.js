import mongoose from 'mongoose';

class Database {
  constructor() {
    this.init();
  }

  init() {
    this.mongoConnection = mongoose
      .connect(
        'mongodb+srv://admin:admin@cluster0-nme7y.mongodb.net/test?retryWrites=true&w=majority',
        {
          useNewUrlParser: true,
          useFindAndModify: true,
        }
      )
      .then(() => console.log('connection successful'))
      .catch(err => console.error(err));
  }
}

export default new Database();
