package cn.edu.pku.rpc;

import cn.edu.pku.rpc.op.AnOtherEchoService;
import cn.edu.pku.rpc.op.AnOtherEchoServiceImpl;
import cn.edu.pku.rpc.op.Echo;
import cn.edu.pku.rpc.op.RemoteEcho;
import cn.edu.pku.rpc.support.Server;
/*
 * �������������server ������,ע��server���ܹ��ṩ�ķ���
 */
public class MainServer {
	public static void main(String[] args) {
		Server server = new RPC.RPCServer();

		/*
		 * server ������,��Ҫע��server���ܹ��ṩ�ķ���,����clientʹ�� ��������֡�
		 * ��������IP���Լ����������еĶ˿� ������ server �ķ���
		 */
		server.register(Echo.class, RemoteEcho.class);//ע����������
		server.register(AnOtherEchoService.class, AnOtherEchoServiceImpl.class);
		
		server.start();//����server
	}
}
