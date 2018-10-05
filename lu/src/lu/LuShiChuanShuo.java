package lu;



import java.util.Scanner;



public class LuShiChuanShuo {
	public static void main(String args[])
	{
		Scanner sc=new Scanner(System.in);
		ZhanChang player1=new ZhanChang();
		ZhanChang player2=new ZhanChang();
		int n;
		n=sc.nextInt();
		int tag=1;
		for(int a=0;a<n;a++)
		{
				String caoZuo;
				caoZuo=sc.next();
				if(caoZuo.equals("end"))
				{
					if(tag==1)
						tag=2;
					else
						tag=1;
				}
				else if(caoZuo.equals("summon"))
				{
					int position;
					int attack;
					int health;
					position=sc.nextInt();
					attack=sc.nextInt();
					health=sc.nextInt();
					if(tag==1)
						player1.summon(position,health,attack);
					else if(tag==2)
						player2.summon(position,health,attack);
					//player1.show();
					//player2.show();
				}
				else if(caoZuo.equals("attack"))
				{
					int ourSuiCong;
					int enemySuiCong;
					ourSuiCong=sc.nextInt();
					enemySuiCong=sc.nextInt();
					if(tag==1)
						ZhanChang.attack(player1.getJueSe(ourSuiCong),player2.getJueSe(enemySuiCong));
					else if(tag==2)
						ZhanChang.attack(player2.getJueSe(ourSuiCong),player1.getJueSe(enemySuiCong));
					player1.qingLiShiTi();
					player2.qingLiShiTi();
					//player1.show();
					//player2.show();
				}
				//System.out.println(tag);
		}
		System.out.println(panShengZhe(player1,player2));
		player1.show();
		player2.show();
	}
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

class YingXiong extends JueSe{
	YingXiong()
	{
		super(30,0);
	}
}

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

class ZhanChang{
	YingXiong player;
	JueSe[] s=new JueSe[8];
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
		System.out.println(s[0].getHealth());
		int num=0;
		for(int a=1;a<8;a++)
		{
			if(s[a].getHealth()>0)
				num++;
		}
		System.out.print(num);
		for(int a=1;a<8;a++)
		{
			if(s[a].getHealth()>0)
			{
				System.out.print(" ");
				System.out.print(s[a].getHealth());
			}
		}
		System.out.print("\n");
	}
}