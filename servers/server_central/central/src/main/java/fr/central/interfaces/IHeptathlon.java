package fr.central.interfaces;

import java.rmi.Remote;

public interface IHeptathlon extends Remote{
    public IData showStocks(Long refernce);
}
