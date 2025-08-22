const {Client, LocalAuth} = require('whatsapp-web.js');
const qrcode = require('qrcode-terminal');

const client = new Client({
    authStrategy: new LocalAuth(),
    puppeteer: {headless: true}
});

client.on('qr', qr => {
    console.log('Scan this QR to connect WhatsApp:');
    qrcode.generate(qr, {small: true});
});

client.on('ready', () => {
    console.log('✅ WhatsApp bot is ready!');
});

client.on('message', message => {
    console.log(`📩 ${message.from}: ${message.body}`);
});

const express = require('express');
const bodyParser = require('body-parser');
const app = express();
app.use(bodyParser.json());

app.post('/send-otp', async (req, res) => {
    const {number, message} = req.body;

    if (!number || !message) {
        return res.status(400).json({error: 'Phone and OTP are required'});
    }

    try {
        const chatId = number + "@c.us";
        await client.sendMessage(chatId, `${message}`);
        res.json({success: true, message: 'OTP sent'});
    } catch (err) {
        console.error(err);
        res.status(500).json({error: 'Failed to send OTP'});
    }
});

client.initialize().then(r => {
    console.log(r)
});
app.listen(3000, () => console.log("📡 WhatsApp API listening on port 3000"));
