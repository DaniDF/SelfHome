package it.dani.selfhomeapp.middle.view.settings;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import it.dani.selfhomeapp.R;
import it.dani.selfhomeapp.middle.cryptography.Asymmetric;
import it.dani.selfhomeapp.middle.view.dialogs.TelegramCodeDialog;

import static it.dani.selfhomeapp.middle.cryptography.Asymmetric.*;

public class SecuritySettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.security_layout);

        Toolbar toolbar = findViewById(R.id.app_toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button genNewKeyPair = findViewById(R.id.genNewKeyPair);
        genNewKeyPair.setOnClickListener(x -> {
            try {
                KeyPair keyPair = Asymmetric.createRSAKey(2048);

                PrintWriter fileOut = new PrintWriter(this.getFilesDir().getPath() + File.separator + "private.telegram.pem");
                fileOut.print(Asymmetric.privateKeyToPem(keyPair.getPrivate()));
                fileOut.close();

                fileOut = new PrintWriter(this.getFilesDir().getPath() + File.separator + "public.telegram.pem");
                fileOut.print(Asymmetric.publicKeyToPem(keyPair.getPublic()));
                fileOut.close();

                Log.v("SelfHome_security", "Generated new RSA key pair");

            } catch (IOException e) {
                Log.e("SelfHome_security", "Error generating new RSA key pair");
                e.printStackTrace();
            }

        });

        Button calcSecureResponse = findViewById(R.id.calcSecureResponse);
        calcSecureResponse.setOnClickListener(x -> {
            TelegramCodeDialog telegramCodeDialog = new TelegramCodeDialog((code) -> {

                try {
                    PrivateKey privateKey = Asymmetric.getPrivateKey(new File(this.getFilesDir().getPath() + File.separator + "private.telegram.pem"));
                    PublicKey publicKey = Asymmetric.getPublicKey(new File(this.getFilesDir().getPath() + File.separator + "public.telegram.pem"));

                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                        String msg = code + Base64.getEncoder().encodeToString(publicKey.getEncoded());
                        String sign = Asymmetric.sign(privateKey,Base64.getEncoder().encodeToString(msg.getBytes()));

                        Log.d("PUBLIC",Base64.getEncoder().encodeToString(publicKey.getEncoded()));

                        Log.d("VERIFY","" + Asymmetric.verify(publicKey,sign,Base64.getEncoder().encodeToString(msg.getBytes())));

                        Log.d("RESULT",msg+sign);
                    }

                } catch (InvalidKeySpecException | InvalidKeyException | SignatureException | IOException e) {
                    e.printStackTrace();
                }


            },null);
            telegramCodeDialog.show(getSupportFragmentManager(),"AUTHENTICATION");
        });
    }
}
