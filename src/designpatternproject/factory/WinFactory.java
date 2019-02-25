package designpatternproject.factory;

import designpatternproject.factory.WinTypes.WinType;

public class WinFactory {
    public static WinGenerator getWin(WinType winType) {
        WinGenerator winGenerator = null;
        switch(winType){
            case  AdminWin:
                winGenerator = new AdminWinGenerator();
                break;
            case StudentWin:
                winGenerator = new StudentWinGenerator();
                break;
        }
        return winGenerator;
    }
}
