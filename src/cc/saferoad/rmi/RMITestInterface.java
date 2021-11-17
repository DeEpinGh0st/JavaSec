package cc.saferoad.rmi;/*
@auther S0cke3t
@date 2021-01-29
*/

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMITestInterface extends Remote {
    String test() throws RemoteException;
}
