import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ServidorUDP {

	public static final int SRVPort=5555;
	public static final String SRVIP="0.0.0.0";
	
	public ServidorUDP(){
		DatagramSocket socket=null;
		//construir un datagrama y esperara a que el cliente le envie el datagrama
		try {
			socket= new DatagramSocket(SRVPort, InetAddress.getByName(SRVIP));
			
			//datagrama apra recibir mensajes por el socket
			DatagramPacket dato= new DatagramPacket(new byte[255], 255);
			
			System.out.println("servidor conectado al socket: "+
								socket.getLocalSocketAddress());
			
			while(true){
				try {	
					
					socket.receive(dato);//es bloqueante	
					System.out.println("recibido dato de: "+dato.getAddress().getHostName()+ " | ");
					//deserializacion, suponiendo que es un string
					String mensaje= new String(dato.getData(), //getdata vuelva el flujo de datos hasta el final y lo desserializa a un string
							dato.getOffset(),
							dato.getLength());
					System.out.println("\nDe longitud: "+mensaje.length());
					System.out.println(mensaje);
					
				} catch (IOException e) {e.printStackTrace();}
			}			
			
		} catch (SocketException | UnknownHostException e) {
			e.printStackTrace();
		}	
		finally {
			if(socket!=null){
				socket.close();
			}
		}
		
	}
	
	public static void main(String[] args) {
		new ServidorUDP();
	}

}
