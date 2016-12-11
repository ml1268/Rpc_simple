package cn.edu.pku.rpc.op;

public class AnOtherEchoServiceImpl implements AnOtherEchoService{

	public String anOtherEcho(String str) {
		// TODO Auto-generated method stub
		return "from remote impl " + str;
	}
	
}
