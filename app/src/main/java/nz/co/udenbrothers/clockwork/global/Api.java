package nz.co.udenbrothers.clockwork.global;

/**
 * Created by user on 28/08/2017.
 */

public class Api {
    public static final String GET_PROJECTS = "https://clockwork-api.azurewebsites.net/v1/projects";
    public static final String GET_SHIFTS = "https://clockwork-api.azurewebsites.net/v1/projects/shifts";
    public static final String SAVE_SHIFTS = "https://clockwork-api.azurewebsites.net/v1/projects/shifts/save";
    public static final String EXPORT_SHIFTS = "https://clockwork-api.azurewebsites.net/v1/projects/export/shifts";
    public static final String SIGNIN = "https://clockwork-api.azurewebsites.net/v1/authentication/login";
    public static final String SIGNUP = "https://clockwork-api.azurewebsites.net/v1/authentication/create";
    public static final String UPGRADE_BUSS = "https://clockwork-api.azurewebsites.net/v1/users/upgrade";
    public static String LINK_BUSS(String cid){return "https://clockwork-api.azurewebsites.net/v1/users/" + cid + "/link";}
    public static final String FORGOT_PASS = "https://clockwork-api.azurewebsites.net/v1/authentication/password/reset/request";
    public static final String GET_USERS = "https://clockwork-api.azurewebsites.net/v1/users";
}
