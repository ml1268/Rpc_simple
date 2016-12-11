package cn.edu.pku.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import cn.edu.pku.rpc.protocal.Invocation;
import cn.edu.pku.rpc.support.Client;
import cn.edu.pku.rpc.support.Listener;
import cn.edu.pku.rpc.support.Server;

public class RPC {
	public static <T> T getProxy(final Class<T> clazz, String host, int port) {

		final Client client = new Client(host, port);//
		InvocationHandler handler = new InvocationHandler() {

			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				Invocation invo = new Invocation();
				invo.setInterfaces(clazz);

				// ���÷�����ƽ�java.lang.reflect.Method ������ķ�����,���� ��װ�� Invocation
				// invo������
				invo.setMethod(new cn.edu.pku.rpc.protocal.Method(method.getName(), method.getParameterTypes()));
				invo.setParams(args);

				/*
				 * ������Ҫ���õ�Զ��server�˵ķ������Ͳ�����װ��invo֮��Client ���� �Ϳ��԰� invo ��Ϊ����
				 * ���ݸ���������. Ϊʲô��Ҫ�������أ�InvocationHandler
				 * ��invoke�������Զ�ִ�еģ��ڸ÷������棬���������ɵĴ������ proxy (��һ������) ��ʵ�ֵĽӿ�(��
				 * Proxy.newProxyInstance()�ĵڶ�������ָ��) �Ϳ���֪������ӿ��ж�������Щ����
				 * InvocationHandler �� invoke �����ĵڶ�������Method method
				 * �Ϳ��Խ������ӿ��еķ������Ͳ����� �����Ƿ�װ��Invocation invo������,�ٽ� invo ��Ϊ
				 * client.invoke(invo)�Ĳ��� ���͵���������
				 */
				client.invoke(invo);// invoke �ȵ���init����һ��Socket����,�ٽ�invo �������������
				return invo.getResult();
			}
		};

		/*
		 * @param Class[]{} �ò��������˶�̬���ɵĴ������ʵ���˵Ľӿ�,�� clazz ������Ľӿ����� .
		 * ����������ɵĴ����������һ������ʵ���˵Ľӿ����͵Ķ��� �Ӷ��Ϳ�����������������ʵ�ֵĽӿ��ж���ķ���
		 * 
		 * @param handler ���ɴ���ʵ������ʱ��Ҫ����һ��handler���� ��������
		 * ����ʵ��������ýӿ��ж���ķ���ʱ,����ί�и�InvocationHandler �ӿ���������invoke����
		 * ��ʱ,InvocationHandler ��invoke �������ᱻ�Զ�����
		 */
		T t = (T) Proxy.newProxyInstance(RPC.class.getClassLoader(), new Class[] { clazz }, handler);
		return t;
	}

	/*
	 * �൱�ڷ��������
	 */
	public static class RPCServer implements Server {
		private int port = 20382;
		private Listener listener;
		private boolean isRuning = true;

		/*
		 * @param isRuning the isRuning to set
		 */
		public void setRuning(boolean isRuning) {
			this.isRuning = isRuning;
		}

		/*
		 * @return the port
		 */
		public int getPort() {
			return port;
		}

		/*
		 * @param port the port to set
		 */
		public void setPort(int port) {
			this.port = port;
		}

		private Map<String, Object> serviceEngine = new HashMap<String, Object>();

		/*
		 * �������˵� call ��������
		 */
		public void call(Invocation invo) {
			System.out.println(invo.getClass().getName());
			Object obj = serviceEngine.get(invo.getInterfaces().getName());
			if (obj != null) {
				try {
					Method m = obj.getClass().getMethod(invo.getMethod().getMethodName(), invo.getMethod().getParams());
					/*
					 * ����JAVA���������ִ��java.lang.reflect.Method ������ķ���
					 * 
					 * @param result : ִ��ʵ�ʷ����� �õ��� �����ִ�н��
					 */
					Object result = m.invoke(obj, invo.getParams());
					invo.setResult(result);// �������ִ�н����װ��invo�����С��ں���Ĵ����У����ö���д�뵽�������
				} catch (Throwable th) {
					th.printStackTrace();
				}
			} else {
				throw new IllegalArgumentException("has no these class");
			}
		}

		/*
		 * @param interfaceDefiner ��Ҫע��Ľӿ�
		 * 
		 * @param impl ע��Ľӿڵ�ʵ����
		 */
		public void register(Class interfaceDefiner, Class impl) {
			try {
				this.serviceEngine.put(interfaceDefiner.getName(), impl.newInstance());
				System.out.println(serviceEngine);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void start() {
			System.out.println("����������");

			/*
			 * server ����ʱ,��ҪListener�����Ƿ���client���������� listener ��һ���߳�,��������������
			 */
			listener = new Listener(this);
			this.isRuning = true;
			listener.start();// listener ��һ���߳���,start()���ִ���̵߳�run����
		}

		public void stop() {
			this.setRuning(false);
		}

		public boolean isRunning() {
			return isRuning;
		}
	}
}