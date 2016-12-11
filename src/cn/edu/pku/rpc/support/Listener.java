package cn.edu.pku.rpc.support;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import cn.edu.pku.rpc.protocal.Invocation;

/*
 * ����һ���߳���,������������һ����ʵ��������Client����
 */
public class Listener extends Thread {
	private ServerSocket server_socket;
	private Server server;

	public Listener(Server server) {
		this.server = server;
	}

	@Override
	public void run() {

		System.out.println("�����������У��򿪶˿�" + server.getPort());
		try {
			server_socket = new ServerSocket(server.getPort());//����һ�������ض��˿ڵ�ServerSocket
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
		while (server.isRunning()) {
			try {
				System.out.println("�ȴ�����");
				/*
				 * accept()��һ����������,server_socket һֱ�ȴ�client �Ƿ������ӵ���
				 */
				Socket client = server_socket.accept();//����һ��TCP����
				System.out.println("������");
				//ִ�е������ʱ,�����Ѿ�������һ������, �������� Socket client ���� ����ʾ
				ObjectInputStream ois = new ObjectInputStream(client.getInputStream());//��ȡclient ����������
				/*
				 * ���������н�����client ���͹���������
				 * client ������Ҫ���õ�Զ��server�ϵķ�������(����)(��������) ͨ��Socket���͵�server
				 * client ���͵��������Զ����Invocation���װ,
				 */
				Invocation invo = (Invocation) ois.readObject();
				
				System.out.println("Զ�̵���:" + invo);

				server.call(invo);//���ͻ��˴��ݵĵ�����������ʵ�ʵĶ������
				
				ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
				/*
				 * ��call(invo)�н����ý�� ��װ��invo ������
				 * �����з�����ý���� invo����д�뵽�������
				 * ������client �Ϳ��Ը��� socket ���������ӴӸ�������л�õ��ý��
				 */
				oos.writeObject(invo);
				oos.flush();
				oos.close();
				ois.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		try {
			if (server_socket != null && !server_socket.isClosed())
				server_socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
