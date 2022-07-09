const dgram = require('dgram');
const fs = require("fs");
const Telegraf = require('telegraf');
const bot = new Telegraf('1220583056:AAFhyZklNklmltRsR35ChMa9jgoXdBMLR_s');
const node_rsa = require('node-rsa');

bot.start(context => start(context));
bot.command('replay',context => replayChallenge(context));
bot.launch();

let randChallengeNumber = 0;

function start(context)
{
    for(let count = 0; count < 6; count++) randChallengeNumber = randChallengeNumber * 10 + ((Math.floor(Math.random() * 10) + 1) % 10);
    context.telegram.sendMessage(context.chat.id, "Hello " + context.chat.username + "☺️\n<b>Challenge this: <code>" + randChallengeNumber + "</code></b>\t/replay",{parse_mode:"HTML"});
    //listGroups(context);
}

function replayChallenge(context)
{
    const publicKey = fs.readFileSync("public.pem");
    /*const privateKey = fs.readFileSync("private.pem");

    let msg = "123456" + publicKey;
    let sign = RSASign(privateKey,msg);*/

    const text = context.update.message.text.substring(8);

    /*console.log("KEY: " + text.substring(6,451+5));
    console.log("SIGN: " + text.substring(451+5));
    console.log("DATA: " + text.substring(0,451+5));*/

    /*if(msg !== text.substring(0,451+6)) console.log("DATA DIFFERS");
    if(sign !== text.substring(451+6)) console.log("SIGN DIFFERS");
    if(publicKey.toString() !== text.substring(6,451+6)) console.log("PUB DIFFERS");*/

    const verify = RSAVerify(text.substring(6,451+6),text.substring(451+6),text.substring(0,451+6));
    //const verify = RSAVerify(publicKey,sign,msg);
    console.log("VERIFY: " + verify);

    randChallengeNumber = 123456;
    if(verify && text.substring(0,6) == randChallengeNumber) listGroups(context);
    else context.telegram.sendMessage(context.chat.id,"<b>NOT AUTHORIZED</b>",{parse_mode:"HTML"});
}

function listGroups(context)
{
    sendToSelfHomeMiddle("LGR",context,listGroupsHandler);
}

function listGroupsHandler(msg,rinfo,context)
{
    let groups = split(msg.toString(),";");
    let inlineKeyboard = [[]];

    for(let count = 0; count < groups.length; count++)
    {
        inlineKeyboard[0][count] = {
            text : groups[count],
            callback_data : "GRUP;" + groups[count]
        }

        bot.action("GRUP;" + groups[count],context => list(context,groups[count]));
    }

    context.telegram.sendMessage(context.chat.id,"Groups:",{
        reply_markup : {
            inline_keyboard : inlineKeyboard
        }
    });
}

function list(context,name)
{
    context.deleteMessage();
    sendToSelfHomeMiddle("LDG;" + name,context,listDevicesHandler);
}

function listDevicesHandler(msg,rinfo,context)
{
    let devices = split(msg.toString(),";");
    let inlineKeyboard = [[]];

    for(let count = 0; count < devices.length; count++)
    {
        inlineKeyboard[0][count] = {
            text : devices[count],
            callback_data : "DISP;" + devices[count]
        }

        bot.action("DISP;" + devices[count],context => changeState(context,devices[count]));
    }

    inlineKeyboard[0][devices.length] = {
        text : "Previous menu",
        callback_data : "BACK;GRUP"
    }

    bot.action("BACK;GRUP",context => backMenu(context));

    context.telegram.sendMessage(context.chat.id,"Devices:",{
        reply_markup : {
            inline_keyboard : inlineKeyboard
        }
    });
}

function changeState(context,name)
{
    context.deleteMessage();
    let inlineKeyboard = [[]];

    inlineKeyboard[0][0] = {
        text : "ON🟡",
        callback_data : "DISP;" + name + ";1"
    }

    bot.action("DISP;" + name + ";1",context => changeStateHandler(context,"DISP;" + name + ";1"));

    inlineKeyboard[0][1] = {
        text : "OFF⚫",
        callback_data : "DISP;" + name + ";0"
    }

    bot.action("DISP;" + name + ";0",context => changeStateHandler(context,"DISP;" + name + ";0"));

    inlineKeyboard[0][2] = {
        text : "Previous menu",
        callback_data : "BACK;DISP"
    }

    bot.action("BACK;DISP",context => backMenu(context));

    context.telegram.sendMessage(context.chat.id,name + ":",{
        reply_markup : {
            inline_keyboard : inlineKeyboard
        }
    });
}

function changeStateHandler(context,name)
{
    sendToSelfHomeMiddle("SET;" + name,context,responseChangeStateHandler);
}

function responseChangeStateHandler(msg,rinfo,context)
{
    context.deleteMessage();
    context.telegram.sendMessage(context.chat.id,"<code>" + msg + "</code>",{parse_mode : "HTML"})
}

function backMenu(context)
{
    context.deleteMessage();
    listGroups(context);
}

function sendToSelfHomeMiddle(msg,context,handler)
{
    let sock = dgram.createSocket('udp4');
    sock.on("message", (msg,rinfo) => handler(msg.toString().substring(0,msg.toString().length - 1),rinfo,context));
    sock.send(msg,4000,"192.168.0.106"/*"localhost"*/);
}

function split(string, char)
{
    let result = [];

    for(let count = 0, index = 0; count < string.length; count++)
    {
        if(string.charAt(count) != char)
        {
            if(result[index] == undefined) result[index] = "";
            result[index] += string.charAt(count);
        }
        else index++;
    }

    return result;
}

function RSASign(privateKey, data) {
    const sign = crypto.createSign('RSA-SHA512');
    sign.update(data);
    var signed = sign.sign(privateKey,'hex');
    return signed;
}

function RSAVerify(publicKey, sign, data) {
    const verify = crypto.createVerify('RSA-SHA512');
    verify.update(data);
    return verify.verify(publicKey,sign,"hex");
}