package cat.udl.tidic.amd.dam_tips.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import cat.udl.tidic.amd.dam_tips.models.Account;
import cat.udl.tidic.amd.dam_tips.services.AccountServiceI;
import cat.udl.tidic.amd.dam_tips.services.AccountServiceImpl;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepo {

    private final String TAG = "AccountRepo";

    private final AccountServiceI accountService;
    private final MutableLiveData<String> mResponseRegister;

    public AccountRepo() {
        this.accountService = new AccountServiceImpl();
        this.mResponseRegister = new MutableLiveData<>();
    }

    public void registerAccount(Account account){

        accountService.register(account).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                int return_code = response.code();  //200, 404, 401,...
                Log.d(TAG,"registerAccount() -> ha rebut el codi: " +  return_code);

                if (return_code == 200){
                    mResponseRegister.setValue("El registre s'ha fet correctament!!!!");
                }else{
                    String error_msg = "Error: " + response.errorBody();
                    mResponseRegister.setValue(error_msg);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                String error_msg = "Error: " + t.getMessage();
                mResponseRegister.setValue(error_msg);
            }
        });

    }


}
