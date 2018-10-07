package lushichuanshuo;
import java.util.Scanner;
import org.apache.log4j.Logger;

import hello.Hello;

import org.apache.log4j.Level;
/**
 *   �����������¯ʯ��˵�ļ���С��Ϸ
 *   
 * <p>������Ҫ��{@code ZhanChang},{@link JueSe},{@link YingXiong}��{@link SuiCong}���.
 * 
 * @author shizhuobin
 *
 */

public class LuShiChuanShuo {
	public static void main(String args[])
	{
	    Logger logger = Logger.getLogger(Hello.class);
	    // �����ִ���ʱ�����ȼ���Ϊdebug���.
	    logger.setLevel(Level.INFO);
		Scanner sc=new Scanner(System.in);
		
		//�����������
		ZhanChang player1=new ZhanChang();
		ZhanChang player2=new ZhanChang();
		int n;
		n=sc.nextInt();
		
		//����tag������ǵ�ǰ��ң�ֵΪ1ʱ��������ң�ֵΪ2ʱ�Ǻ������.
		int tag=1;
		for(int a=0;a<n;a++)
		{
				String caoZuo;
				caoZuo=sc.next();
				if(caoZuo.equals("end"))
				{
					logger.debug(tag);
					if(tag==1)
						tag=2;
					else
						tag=1;
					logger.debug(tag);
				}
				else if(caoZuo.equals("summon"))
				{
					int position;
					int attack;
					int health;
					position=sc.nextInt();
					attack=sc.nextInt();
					health=sc.nextInt();
					
					//�ٻ����
					if(tag==1)
						player1.summon(position,health,attack);
					else if(tag==2)
						player2.summon(position,health,attack);
					logger.debug("1");
				}
				else if(caoZuo.equals("attack"))
				{
					int ourSuiCong;
					int enemySuiCong;
					ourSuiCong=sc.nextInt();
					enemySuiCong=sc.nextInt();
					
					//��ɫ֮��Ĺ���
					if(tag==1)
					{
						ZhanChang.attack(player1.getJueSe(ourSuiCong),player2.getJueSe(enemySuiCong));
						logger.debug("2");
					}
					else if(tag==2)
					{
						ZhanChang.attack(player2.getJueSe(ourSuiCong),player1.getJueSe(enemySuiCong));
						logger.debug("3");
					}
					
					// ����˫�����������.
					player1.qingLiShiTi();
					player2.qingLiShiTi();
					logger.debug("4");
				}
				logger.debug(tag);
		}
		
		//���ս�����
		System.out.println(panShengZhe(player1,player2));
		player1.show();
		player2.show();
	}
	/**
	 * 
	 * ����Ϸʤ��
	 * 
	 * @param a
	 * @param b
	 * @return {@code 1} ����������Ӯ.
	 * @return {@code -1} ����������Ӯ.
	 * @return {@code 0} ���ƽ��
	 */
	static int panShengZhe(ZhanChang a,ZhanChang b)
	{
		if(a.getYingXiongHealth()<=0)
			return -1;
		else if(b.getYingXiongHealth()<=0)
			return 1;
		else
			return 0;
	}
}
/**
 * {@code JueSe}������ṩ����Ϸ�еĽ�ɫ����������ֵ�͹����������ԣ����б�����ʱ��Ѫ�ķ���{@link beiGongJi}.
 */
class JueSe{
	int health;
	int attack;
	JueSe()
	{
		health=0;
		attack=0;
	}
	JueSe(int a,int b)
	{
		health=a;
		attack=b;
	}
	int getHealth()
	{
		return health;
	}
	int getAttack()
	{
		return attack;
	}
	void beiGongJi(JueSe a)
	{
		health-=a.getAttack();
	}
}
/**
 * ��һ������ֵΪ30��������Ϊ0�Ľ�ɫ
 */
class YingXiong extends JueSe{
	YingXiong()
	{
		super(30,0);
	}
}
/**
 * �Զ�������ֵ�͹������Ľ�ɫ 
 */
class SuiCong extends JueSe{
	SuiCong()
	{
		super(0,0);
	}
	SuiCong(int a,int b)
	{
		super(a,b); 
	}
}
/**
 * һ��ս����ʾһ����ҡ������ٻ���ӡ����֮��Ĺ���������ʬ���չʾս���ȷ���.��һ����ɫ����s��¼��ɫ�ı��.
 */
class ZhanChang{
	YingXiong player;
	JueSe[] s=new JueSe[8];
	Logger logger = Logger.getLogger(ZhanChang.class);
	
	ZhanChang()
	{
		s[0]=new YingXiong();
		for(int a=1;a<8;a++)
		{
			s[a]=new SuiCong();
		}
	}
	void summon(int position,int health,int attack)
	{
		if(s[position].getHealth()!=0)
		{
			int tag;
			for(tag=position+1;tag<8;tag++)
			{
				if(s[tag].getHealth()==0)
				{
					break;
				}
			}
			for(;tag>position;tag--)
			{
				s[tag]=s[tag-1];
			}
		}
		s[position]=new SuiCong(health,attack);
	}
	static void attack(JueSe a,JueSe b)
	{
		a.beiGongJi(b);
		b.beiGongJi(a);
	}
	void qingLiShiTi()
	{
		for(int a=0;a<8;a++)
		{
			if(s[a].getHealth()<=0&&s[a].getAttack()!=0)
			{
				for(int b=a;b<7;b++)
				{
					s[b]=s[b+1];
				}
				s[7]=new SuiCong();
				break;
			}
		}
	}
	JueSe getJueSe(int n)
	{
		return s[n];
	}
	int getYingXiongHealth()
	{
		return s[0].getHealth();
	}
	void show()
	{
		logger.setLevel(Level.INFO);
		System.out.println(s[0].getHealth());
		int num=0;
		for(int a=1;a<8;a++)
		{
			if(s[a].getHealth()>0)
			{
				num++;
				logger.debug("find a suicong");
			}
		}
		System.out.print(num);
		for(int a=1;a<8;a++)
		{
			if(s[a].getHealth()>0)
			{
				System.out.print(" ");
				System.out.print(s[a].getHealth());
				logger.debug("5");
			}
		}
		System.out.print("\n");
	}
}

class KaZu{
	JueSe[] s=new JueSe[20];
	KaZu()
	{
		for(int a=0;a<20;a++)
		{
			int x=(int)(Math.random()*30);
			int y=(int)(Math.random()*30);
			s[a]=new JueSe(x,y);
		}
	}
	JueSe chouKa()
	{
		int x=(int)(Math.random()*20);
		return s[x];
	}
}
