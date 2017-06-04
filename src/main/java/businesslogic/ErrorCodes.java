package businesslogic;

/**
 * Created by Danya on 04.06.2017.
 */
public class ErrorCodes
{
    public static int success = 0;
    public static int driverIsNotAgree = 1;
    public static int driverIsBusy = 2;
    public static int driversVehicle = 3;
    public static int wrongMoney = 4;
    public static int cantAddComm = 5;
    public static int driverWIthoutVehicle = 6;
    public static int excursionAlreadyHaveADriver = 7;
    public static int excursionIsNull = 8;
    public static int maxTourists = 9;
    public static int minTourists = 10;
    public static int late = 11;
    public static int userAlreadyInExcursion = 12;

    public static String getError(int num){
        switch (num){
            case 1:
                return "Driver is not Agree";
            case 2:
                return "Driver is busy";
            case 3:
                return "Drivers vehicle is not checked";
            case 4:
                return "Money is less then price";
            case 5:
                return "You cant add comments";
            case 6:
                return "Driver without vehicle";
            case 7:
                return "Excursion already have a driver";
            case 8:
                return "First create an excursion";
            case 9:
                return "Tourists limit is exceeded";
            case 10:
                return "Too few tourists";
            case 11:
                return "You're late";
            case 12:
                return "You're already in excursion";
        }
        return "Error!";
    }

}
