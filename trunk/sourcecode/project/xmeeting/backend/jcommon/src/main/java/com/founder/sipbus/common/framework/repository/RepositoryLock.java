/**
 * Copyright c FOUNDER CORPORATION 2006 All Rights Reserved.
 * RepositoryLock.java
 */
package com.founder.sipbus.common.framework.repository;

/**
 * [クラス名]<br>
 * RepositoryLockクラス。<br>
 * <br>
 * [機能概要]<br> 
 * 多线程读写配置信息时，控制读写的时机，保证多线程下数据的正确性和安全性<br>
 * <br>
 * [変更履歴]<br>
 * 2007/04/21 新規作成 weifeng<br>
 * 
 * @author weifeng
 * @version 1.00
 */
public class RepositoryLock {

    private volatile int getterCount = 0;

    private volatile int setterCount = 0;

    private volatile boolean preferSetter = true;

    private volatile boolean setting = false;

    /**
     * 読み込み処理の開始を宣言します。
     */
    public synchronized void beginGet() {
        while (setting || (preferSetter && setterCount > 0)) {
            try {
                wait();
            } catch (InterruptedException e) {
                // do nothing
            }
        }
        getterCount++;
    }

    /**
     * 読み込み処理の終了を宣言します。
     */
    public synchronized void endGet() {
        getterCount--;
        preferSetter = true;
        notifyAll();
    }

    /**
     * 書き込み処理の開始を宣言します。
     */
    public synchronized void beginSet() {
        setterCount++;
        while (setting || getterCount > 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                // do nothing
            }
        }
        setterCount--;
        setting = true;
    }

    /**
     * 書き込み処理の終了を宣言します。
     */
    public synchronized void endSet() {
        setting = false;
        preferSetter = false;
        notifyAll();
    }
}
