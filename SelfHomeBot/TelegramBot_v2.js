const dgram = require('dgram');
const fs = require("fs");
const Telegraf = require('telegraf');
const bot = new Telegraf('1220583056:AAFhyZklNklmltRsR35ChMa9jgoXdBMLR_s');

const authorizedUsers = ["DaniPerenna"]; //TODO load from file

bot.start(context => start(context));
bot.command('execute',context => sendRequest(context));
bot.launch();

console.log("Start bot")

function start(context)
{
    context.telegram.sendMessage(context.chat.id,"HOME <i>Sweet home</i>",{parse_mode : "HTML"})
}

function sendRequest(context)
{
    if(authorizedUsers.indexOf(context.chat.username) >= 0)
    {
        let message = context.update.message.text;
        message = message.substring("/execute".length).trim();
        console.log("Receved = " + message);
        sendToSelfHomeMiddle(message,(resp,info) => {
            context.telegram.sendMessage(context.chat.id,"Response:\n\n<code>" + resp + "</code>", {parse_mode : "HTML"});
        });
    }
}

function sendToSelfHomeMiddle(msg,handler)
{
    let sock = dgram.createSocket('udp4');
    sock.on("message", (msg,rinfo) => handler(msg.toString().substring(0,msg.toString().length - 1),rinfo));
    sock.send(msg,4000,"10.0.0.106"/*"localhost"*/);
}