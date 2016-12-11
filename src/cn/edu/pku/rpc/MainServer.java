package cn.edu.pku.rpc;

import cn.edu.pku.rpc.op.AnOtherEchoService;
import cn.edu.pku.rpc.op.AnOtherEchoServiceImpl;
import cn.edu.pku.rpc.op.Echo;
import cn.edu.pku.rpc.op.RemoteEcho;
import cn.edu.pku.rpc.support.Server;
/*
 * 服务端启动程序，server 启动后,注册server端能够提供的服务
 */
public class MainServer {
	public static void main(String[] args) {
		Server server = new RPC.RPCServer();

		/*
		 * server 启动后,需要注册server端能够提供的服务,这样client使用 服务的名字、
		 * 服务器的IP、以及服务所运行的端口 来调用 server 的服务
		 */
		server.register(Echo.class, RemoteEcho.class);//注册服务的名字
		server.register(AnOtherEchoService.class, AnOtherEchoServiceImpl.class);
		
		server.start();//启动server
	}
}
