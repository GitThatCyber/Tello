import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

public class Control {

    DatagramSocket cntDs, stDs;
    InetAddress cntAddr;
    int cntPrt;
    String str;
    

    public Control() {
        try {
            cntAddr = InetAddress.getByName("192.168.10.1");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        cntPrt = 8889;
    }

    public void connect() throws SocketException {
        cntDs = new DatagramSocket(cntPrt);
        cntDs.connect(cntAddr, cntPrt);
        System.out.println("CmdRcv Socket connection status: " + cntDs.isConnected());
        
        // initiate sdk mode
        byte id[];
        str = "command";
        id = str.getBytes();
        DatagramPacket SDKDp = new DatagramPacket(id, id.length, cntAddr, cntPrt);
        try {
            cntDs.send(SDKDp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String sendCmd(byte sd[]) {
        // send command
        DatagramPacket sendDp = new DatagramPacket(sd, sd.length, cntAddr, cntPrt);
        try {
            cntDs.send(sendDp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // process ack
        byte rd[] = new byte[256];
        String tr;
        DatagramPacket receiveDp = new DatagramPacket(rd, rd.length, cntAddr, cntPrt);
        try {
            cntDs.receive(receiveDp);
        } catch (IOException e) {
            e.printStackTrace();
        }
        tr = new String(receiveDp.getData(), StandardCharsets.UTF_8);
        return tr;
    }

    public String decodeCmd(int iIn) {
        String command = "";
        switch (iIn) {
            case 90: //z key
                command = "takeoff";
                break;
            case 88: //x key
                command = "land";
                break;
            case 87: //w key
                command = "forward 100";
                break;
            case 83: //s key
                command = "back 100";
                break;
            case 65: //a key
                command = "left 100";
                break;
            case 68: //d key
                command = "right 100";
            break;
            case 38: //up arrow
                command = "up 100";
            break;
            case 40: //down arrow
                command = "down 100";
            break;
            case 37: //left arrow
                command = "ccw 45";
            break;
            case 39: //right arrow
                command = "cw 45";
            break;
            case 32: //space bar
                command = "stop";
            break;
            default:
                break;
        }
        return command;
    }

    public void disconnect() {
        cntDs.disconnect();
    }

}