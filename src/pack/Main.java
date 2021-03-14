package pack;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class Main {
	static BufferedReader br= new BufferedReader(new InputStreamReader(System.in));
	static BufferedWriter bw= new BufferedWriter(new OutputStreamWriter(System.out));
	public static List<Integer> prices= new ArrayList<>();
	
	public static void main(String[] args) throws IOException {
		
		int i=0;
		int cash;
		int price1=-1;
		int price2=-1;
		
		String cases=null;
		
		String strBooksNumber=br.readLine();
		
		while(strBooksNumber!=null) {//mientras no encuentre el EOF
			if(!strBooksNumber.equals("")) {
				cases=br.readLine();
				cash=Integer.parseInt(br.readLine());
				prices=convertStringToIntegerArray(cases);
				SortPricesList();
				int pos= binarySearch(prices,cash);
				deletePrices(pos);
				bw.write(booksElection(cash, price1, price2, i)+"\n");
			}
			strBooksNumber=br.readLine();
		}
      
		br.close();
		bw.close();
	}
	public static void deletePrices(int pos) {
		//ELIMINO LOS PRECIOS MAYORES O IGUALES A LA CANTIDAD DE DINERO DISPONIBLE		
		if(pos>=0) {
			while (prices.get(pos) == prices.get(pos-1)) {
					pos=pos-1;		
			}
			while(pos<=prices.size()-1) {
				prices.remove(pos);	
			}
		}
	}
	
	public static int binarySearch(List<Integer> prices, int searchValue) {
		int pos=-1;
		int i=0;
		int j=prices.size()-1;
		
		while(i<=j && pos<0) {
			int m=(i+j)/2;
			if(prices.get(m)==searchValue) {
				pos=m;
			}
			else if(prices.get(m)>searchValue) {
				j=m-1;
			}
			else {
				i=m+1;
			}
		}
		return pos;
		
	}
	public static String booksElection(int cash, int price1, int price2, int i) {
		String message;
				//DO THE PROCESS UNTIL THE SIZE OF PRICES IS 1
				while((prices.size()-1)>0) {
					int m= (prices.size()-1)/2; //number of the middle of the list
					int num=prices.get(m);		//price that is in the middle of the list
					int searchNumber=cash-num;	//Number that is going to be searched in the list
					int searchNumberPosition= binarySearch(prices, searchNumber);
					
					//If the number in the middle is less than the searched number they are changed so num-searchNumber is not going to be <0
					if(num<searchNumber) {
						int temp=num;
						num=searchNumber;
						searchNumber=temp;
					}
					//If the number searched exists
					if(searchNumberPosition>=0 && searchNumberPosition!=m ) {
						//If the prices has never been changed they are assigned to price1 and price2 and removed from the list
						if(price1<0 && price2<0) {
							price1=num;
							price2=searchNumber;
							if(price1<price2) {
								int temp=price1;
								price1=price2;
								price2=temp;
							}
							prices.remove(searchNumberPosition);
							prices.remove(m);
						}
						//if the prices has been changed the diference between the new values is compared to the diference of the existings prices
						else if(price1>=0 && price2>=0) {
							//If the difference is less we change the price1 and price 2 and remove them from the list
							if ((num-searchNumber)<(price1-price2)) {
								price1=num;
								price2=searchNumber;
								if(price1<price2) {
									int temp=price1;
									price1=price2;
									price2=temp;
								}
								prices.remove(searchNumberPosition);
								prices.remove(m);
							}
							//If the difference is more we remove it because that numbers are not helpfull
							else {
								prices.remove(searchNumberPosition);
								prices.remove(m);
							}
						}
					}
					//If the number doesn't exists
					else {
						//If is the first iteration
						if(i==0 && searchNumberPosition>=0) {
							if(searchNumberPosition==m) {
								prices.remove(m);
							}
							else {
								price1=num;
								price2=searchNumber;
								prices.remove(m);
							}
						}
						//if is not the first iteration
						else {
							prices.remove(m);
						}
					}
					
					i++;
				}
				message="Peter should buy books whose prices are "+price2+" and "+price1+"."+"\n";
				return message;
				
	}
	
	public static List<Integer> convertStringToIntegerArray(String data) throws IOException {
		String[] parts = data.split(" ");
		List<Integer> prices= new ArrayList<>();
		for(int i=0;i<parts.length;i++) {
			prices.add(Integer.parseInt(parts[i]));
		}
		return prices;
		
	}
	
	public static void SortPricesList() {
		for(int i=1;i<prices.size();i++) {
			for(int j=i; j>0 && prices.get(j-1)>prices.get(j);j--) {
				int temp=prices.get(j);
				prices.set(j, prices.get(j-1));
				prices.set(j-1, temp);
				
			}
		}
	}

}