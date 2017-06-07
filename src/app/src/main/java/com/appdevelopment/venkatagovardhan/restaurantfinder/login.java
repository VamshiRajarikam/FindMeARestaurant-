package com.appdevelopment.venkatagovardhan.restaurantfinder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

/**
 * Created by Venkata Govardhan on 2/16/2016.
 */
public class login extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    datatbase helper = new datatbase(this);
    private GoogleApiClient mGoogleApiClient;
    private LoginButton facebookLoginButton;
    private CallbackManager callbackManager;
    private SignInButton mPlusSignInButton;
    /* Is there a ConnectionResult resolution in progress? */
    private boolean mIsResolving = false;
    /* Should we automatically resolve ConnectionResults when possible? */
    private boolean mShouldResolve = false;
    ProgressDialog ringProgressDialog;
    private static final int RC_SIGN_IN = 0;
    public static final String TAG_EMAIL = "email";
    public static final String TAG_LOGIN = "login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DisplayMetrics dm= new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int) (width * .9), (int) (height * .7));
        initInstances();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    private void initInstances() {

        //Google+ Login
        mPlusSignInButton = (SignInButton) findViewById(R.id.g_sign_in_button);
        mPlusSignInButton.setSize(SignInButton.SIZE_WIDE);
        mPlusSignInButton.setOnClickListener(this.mPlusSignInButton);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();
        //Facebook Login
//        facebookLoginButton = (LoginButton)findViewById(R.id.flogin);
//        facebookLoginButton.setReadPermissions(Arrays.asList("public_profile, email, user_birthday, user_friends"));
//
//        callbackManager = CallbackManager.Factory.create();

        // Callback registration
//        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                // App code
//                GraphRequest request = GraphRequest.newMeRequest(
//                        loginResult.getAccessToken(),
//                        new GraphRequest.GraphJSONObjectCallback() {
//                            @Override
//                            public void onCompleted(
//                                    JSONObject object,
//                                    GraphResponse response) {
//                                // Application code
//                            }
//                        });
//
//                Bundle parameters = new Bundle();
//                parameters.putString("fields", "id,name,email,gender, birthday");
//                request.setParameters(parameters);
//                request.executeAsync();
//            }

//            @Override
//            public void onCancel() {
//                Toast.makeText(login.this, "User cancelled", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(FacebookException exception) {
//                Toast.makeText(login.this, "Error on Login, check your facebook app_id", Toast.LENGTH_LONG).show();
//            }
//        });
//
//        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
//
  }



    public void checkCredentials(View v)
    {
        EditText usernameCtrl = (EditText)findViewById(R.id.Text_Username);
        EditText pwordCtrl = (EditText) findViewById(R.id.Text_password);

        String userName = usernameCtrl.getText().toString();
        String password = pwordCtrl.getText().toString();




        String pword = helper.searchPass(userName);
        if(password.equals(pword))
        {
            Intent i = new Intent(login.this,home.class);
            i.putExtra("Username",userName);
            startActivity(i);
        }
        else
        {
            Toast temp = Toast.makeText(login.this , "Username and password don't match!" , Toast.LENGTH_SHORT);
            temp.show();
        }



    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id. action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onSignInClicked() {
//        toastLoading.show();
        // User clicked the sign-in button, so begin the sign-in process and automatically
        // attempt to resolve any errors that occur.
        ringProgressDialog = ProgressDialog.show(login.this, "Connecting...", "Atempting to connect", true);
        ringProgressDialog.setCancelable(false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mShouldResolve = true;
                    mGoogleApiClient.connect();
                } catch (Exception e) {
                    ringProgressDialog.dismiss();
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void getProfileInformation() {
        ringProgressDialog.dismiss();
        if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
            Person currentPerson = Plus.PeopleApi
                    .getCurrentPerson(mGoogleApiClient);
            String personName = currentPerson.getDisplayName();
            String personPhotoUrl = currentPerson.getImage().getUrl();
            String personGooglePlusProfile = currentPerson.getUrl();
            String birth = currentPerson.getBirthday();
            String email = Plus.AccountApi.getAccountName(mGoogleApiClient);

            // by default the profile url gives 50x50 px image only
            // we can replace the value with whatever dimension we want by
            // replacing sz=X
//                personPhotoUrl = personPhotoUrl.substring(0,
//                        personPhotoUrl.length() - 2)
//                        + PROFILE_PIC_SIZE;

            //new LoadProfileImage().execute(personPhotoUrl);

        } else {
            Toast.makeText(getApplicationContext(),
                    "Person information is null", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        mShouldResolve = false;
        getProfileInformation();
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        ringProgressDialog.dismiss();
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // Could not connect to Google Play Services.  The user needs to select an account,
        // grant permissions or resolve an error in order to sign in. Refer to the javadoc for
        // ConnectionResult to see possible error codes.
        Log.d(TAG_LOGIN, "onConnectionFailed:" + connectionResult);
        ringProgressDialog.dismiss();

        if (!mIsResolving && mShouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    mIsResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    Log.e(TAG_LOGIN, "Could not resolve ConnectionResult.", e);
                    Toast.makeText(login.this, "Could not resolve ConnectionResult", Toast.LENGTH_LONG).show();
                    mIsResolving = false;
                }
            } else {
                // Could not resolve the connection result, show the user an
                // error dialog.
                Toast.makeText(login.this, "Error on Login, check your google + login method", Toast.LENGTH_LONG).show();
            }
        } else {
            // Show the signed-out UI
        }
    }
}
