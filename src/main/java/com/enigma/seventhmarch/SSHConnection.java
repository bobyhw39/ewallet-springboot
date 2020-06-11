package com.enigma.seventhmarch;

import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SSHConnection {
    private final static String S_PATH_FILE_PRIVATE_KEY = "C:\\Users\\User\\Desktop\\aws\\boby-test.pem"; //windows absolut path of our ssh private key locally saved
//    private final static String S_PATH_FILE_KNOWN_HOSTS = "C:\\Users\\Val\\.ssh\\known_hosts";
    private final static String S_PASS_PHRASE = "";
    private final static int LOCAl_PORT = 3306;
    private final static int REMOTE_PORT = 3306;
    private final static int SSH_REMOTE_PORT = 22;
    private final static String SSH_USER = "ubuntu";
    private final static String SSH_REMOTE_SERVER = "ec2-52-221-199-73.ap-southeast-1.compute.amazonaws.com";
    private final static String MYSQL_REMOTE_SERVER = "52.221.199.73";

    private Session sesion; //represents each ssh session

    public void closeSSH ()
    {
        sesion.disconnect();
    }

    public SSHConnection () throws Throwable
    {

        JSch jsch = null;

        jsch = new JSch();
//        jsch.setKnownHosts(S_PATH_FILE_KNOWN_HOSTS);
        jsch.addIdentity(S_PATH_FILE_PRIVATE_KEY, S_PASS_PHRASE.getBytes());

        sesion = jsch.getSession(SSH_USER, SSH_REMOTE_SERVER, SSH_REMOTE_PORT);
        sesion.connect(); //ssh connection established!

        //by security policy, you must connect through a fowarded port
        sesion.setPortForwardingL(LOCAl_PORT, MYSQL_REMOTE_SERVER, REMOTE_PORT);

    }
}
