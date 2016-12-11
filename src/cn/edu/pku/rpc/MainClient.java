package cn.edu.pku.rpc;

import cn.edu.pku.rpc.op.AnOtherEchoService;
import cn.edu.pku.rpc.op.Echo;

/* 
 * �ͻ��˳��� 
 * ʹ�ö�̬����,ͨ��getProxy������̬��������һ�� Echo���͵Ĵ������echo,�ô������ʵ����Echo�ӿ�
 * ��ˣ�����ʹ�øö�̬���صõ���echo ����������  Echo �ӿ��ж����echo ����
 * �ڿͻ���ֻ��Ҫ�ṩ��Ҫ���õķ�������:Echo ,�÷������ڵķ�������ַ:127.0.0.1,�Լ��˿�:20382
 * �Ϳ��Ե��÷������ϵķ�����.
 */
public class MainClient {
	public static void main(String[] args) {
		/*
		 * ��̬����,ͨ��getProxy������̬��������һ�� Echo���͵Ĵ������echo,�ô������ʵ����Echo�ӿ�
		 * ��ˣ�����ʹ�øö�̬���صõ���echo ���������� Echo �ӿ��ж����echo ����
		 * 
		 * �ڿͻ���ֻ��Ҫ�ṩ��Ҫ���õķ�������:Echo ,�÷������ڵķ�������ַ:127.0.0.1,�Լ��˿�:20382
		 * �Ϳ��Ե��÷������ϵķ�����.
		 */
		Echo echo = RPC.getProxy(Echo.class, "127.0.0.1", 20382);

		/*
		 * ��ִ�� echo.echo("hello,hello") ʱ,����ί�и���̬����InvocationHandlerִ��invoke����
		 * 
		 */
		System.out.println(echo.echo("hello,hello"));// ʹ�ô��������÷������ķ���.����������

		AnOtherEchoService otherEcho = RPC.getProxy(AnOtherEchoService.class, "127.0.0.1", 20382);
		System.out.println(otherEcho.anOtherEcho("hello, world"));
	}
}
