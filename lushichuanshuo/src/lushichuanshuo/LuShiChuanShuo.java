package lushichuanshuo;
import java.util.Scanner;
import org.apache.log4j.Logger;

import hello.Hello;

import org.apache.log4j.Level;
/**
 *   这个类是类似炉石传说的简易小游戏
 *   
 * <p>该类主要由{@code ZhanChang},{@link JueSe},{@link YingXiong}和{@link SuiCong}组成.
 * 
 * @author shizhuobin
 *
 */

public class LuShiChuanShuo {
	public static void main(String args[])
	{
	    Logger logger = Logger.getLogger(Hello.class);
	    // 当出现错误时，将等级调为debug检查.
	    logger.setLevel(Level.INFO);
		Scanner sc=new Scanner(System.in);
		
		//创建两个玩家
		ZhanChang player1=new ZhanChang();
		ZhanChang player2=new ZhanChang();
		int n;
		n=sc.nextInt();
		
		//变量tag用来标记当前玩家，值为1时是先手玩家，值为2时是后手玩家.
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
					
					//召唤随从
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
					
					//角色之间的攻击
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
					
					// 清理双方死掉的随从.
					player1.qingLiShiTi();
					player2.qingLiShiTi();
					logger.debug("4");
				}
				logger.debug(tag);
		}
		
		//输出战局情况
		System.out.println(panShengZhe(player1,player2));
		player1.show();
		player2.show();
	}
	/**
	 * 
	 * 判游戏胜负
	 * 
	 * @param a
	 * @param b
	 * @return {@code 1} 如果先手玩家赢.
	 * @return {@code -1} 如果后手玩家赢.
	 * @return {@code 0} 如果平手
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
 * {@code JueSe}这个类提供了游戏中的角色，具有生命值和攻击力的属性，具有被攻击时扣血的方法{@link beiGongJi}.
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
 * 是一个生命值为30，攻击力为0的角色
 */
class YingXiong extends JueSe{
	YingXiong()
	{
		super(30,0);
	}
}
/**
 * 自定义生命值和攻击力的角色 
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
 * 一个战场表示一个玩家。具有召唤随从、随从之间的攻击、清理尸体和展示战况等方法.用一个角色数组s记录角色的编号.
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
