package neg5.service.controllers;

import com.google.inject.Inject;
import neg5.domain.api.AccountApi;
import org.eclipse.jetty.http.HttpStatus;
import neg5.domain.api.AccountDTO;
import neg5.service.auth.LoginAuthenticator;
import neg5.service.auth.LoginCreds;
import neg5.userData.CurrentUserContext;
import neg5.userData.DuplicateLoginException;
import neg5.domain.api.AccountCreationDTO;
import neg5.service.util.RequestHelper;
import spark.Request;
import spark.Response;

public class AccountController extends AbstractJsonController {

    private final AccountApi accountManager;
    private final RequestHelper requestHelper;
    private final CurrentUserContext currentUserContext;
    private final LoginAuthenticator loginAuthenticator;

    @Inject
    public AccountController(AccountApi accountManager,
                             RequestHelper requestHelper,
                             CurrentUserContext currentUserContext,
                             LoginAuthenticator loginAuthenticator) {
        this.accountManager = accountManager;
        this.requestHelper = requestHelper;
        this.currentUserContext = currentUserContext;
        this.loginAuthenticator = loginAuthenticator;
    }

    @Override
    protected String getBasePath() {
        return "/neg5-api/accounts";
    }

    @Override
    public void registerRoutes() {
        post("", this::createAccountAndLogin);
        get("/me", (request, response) -> currentUserContext.getUserData().orElse(null));
    }

    private Object createAccountAndLogin(Request request, Response response) {
        try {
            AccountCreationDTO account = requestHelper.readFromRequest(request, AccountCreationDTO.class);
            AccountDTO createdAccount = accountManager.createAccount(account);
            loginAuthenticator.loginByCredentials(buildLoginCreds(account), response);
            return createdAccount;
        } catch (DuplicateLoginException e) {
            response.status(HttpStatus.FORBIDDEN_403);
            return "A login with this username already exists";
        }
    }

    private LoginCreds buildLoginCreds(AccountCreationDTO accountCreationDTO) {
        LoginCreds loginCreds = new LoginCreds();
        loginCreds.setPassword(accountCreationDTO.getPassword());
        loginCreds.setUsername(accountCreationDTO.getUsername());

        return loginCreds;
    }
}
