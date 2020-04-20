import User from '../schemas/User';

class UserController {
  async store(req, res) {
    const userExists = await User.findOne({ email: req.body.email });
    if (userExists) {
      return res.status(400).json({ error: 'User already exists' });
    }

    const { name, email } = await User.create(req.body);

    return res.json({
      name,
      email,
    });
  }

  async list(req, res) {
    let users = await User.find({});
    const filterUsers = users.map(user => {
      return {
        id: user._id,
        name: user.name,
        email: user.email,
        inTravel: user.inTravel,
        createdAt: user.createdAt,
        updatedAt: user.updatedAt,
      };
    });
    users = {};
    return res.json(filterUsers);
  }

  async addWallet(req, res) {
    async function checkWallets(err, doc) {
      if (err) return res.status(400).json({ error: 'User not found!' });

      const { wallets } = doc;

      const walletExists = wallets.find(wallet => {
        return wallet.number === req.body.number;
      });

      if (walletExists) {
        return res.status(400).json({ error: 'Wallet already exists!' });
      }

      await User.findByIdAndUpdate(req.params.id, {
        $push: {
          wallets: {
            number: req.body.number,
            flag: req.body.flag,
            verification_code: req.body.verification_code,
          },
        },
      });

      return res.json({ message: 'ok' });
    }

    User.findById(req.params.id, checkWallets);
  }
}

export default new UserController();
