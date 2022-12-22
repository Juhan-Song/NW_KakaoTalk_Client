package Object;

import Utilization.Util;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Container {
    private JPanel panel;

    public JPanel getContainer() {
        return panel;
    }

    public Container(String user, Socket socket) {
        panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));

        JLabel userName = new JLabel(user);
        //JLabel userMessage = new JLabel(message);
        userName.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        }); // 유저 정보 클릭 시

        panel.add(userName);
        //panel.add(userMessage);
    } // 유저 정보 저장하는 컨테이너 (message도 보일 수 있게 수정 중)
}
