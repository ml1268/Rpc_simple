package cn.edu.pku.rpc.op;

/*
 * �ӿڵľ���ʵ���࣬������ʵ���˷������ܹ��ṩ���ַ���
 * ��������ע���,client �Ϳ���Զ�̵�������
 */
public class RemoteEcho implements Echo{
	public String echo(String echo) {
		return "from remote:"+echo;
	}
}
