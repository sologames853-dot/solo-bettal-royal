const express = require('express');
const mongoose = require('mongoose');
const cors = require('cors');
require('dotenv').config();

const app = express();
app.use(cors());
app.use(express.json());

// MongoDB Connection
// Replace 'YOUR_MONGODB_ATLAS_CONNECTION_STRING' with your actual string in .env file
const mongoURI = process.env.MONGO_URI || 'mongodb+srv://admin:password@cluster.mongodb.net/solofire?retryWrites=true&w=majority';

mongoose.connect(mongoURI, {
    useNewUrlParser: true,
    useUnifiedTopology: true
}).then(() => console.log('MongoDB Atlas Connected'))
  .catch(err => console.log('Error connecting to MongoDB:', err));

// Schemas
const CharacterSchema = new mongoose.Schema({
    id: String,
    name: String,
    description: String,
    modelUrl: String,
    speed: Number,
    health: Number
});

const EventSchema = new mongoose.Schema({
    id: String,
    title: String,
    description: String,
    imageUrl: String,
    expiryDate: Number
});

const Character = mongoose.model('Character', CharacterSchema);
const GameEvent = mongoose.model('Event', EventSchema);

// Routes
app.get('/api/characters', async (req, res) => {
    const characters = await Character.find();
    res.json(characters);
});

app.post('/api/characters', async (req, res) => {
    const newChar = new Character(req.body);
    await newChar.save();
    res.json({ message: 'Character added successfully' });
});

app.get('/api/events', async (req, res) => {
    const events = await GameEvent.find();
    res.json(events);
});

app.post('/api/events', async (req, res) => {
    const newEvent = new GameEvent(req.body);
    await newEvent.save();
    res.json({ message: 'Event added successfully' });
});

const PORT = process.env.PORT || 5000;
app.listen(PORT, () => console.log(`Server running on port ${PORT}`));
