import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.plaf.metal.MetalPopupMenuSeparatorUI;

public class ClienteUDP {

	//servidor
	public static final int SRVPort=5555;
	//public static final String SRVIP="192.168.3.63";
	public static final String SRVIP="localhost";
	
	//cliente
	public static final int CLIPort=5556;
	//public static final String CLIIP="192.168.3.63";
	public static final String CLIIP="0.0.0.0";
	
	//datos que se van a enviar y recibir
	private byte[] datosEnviados= new byte[255];
	private byte[] datosRecibidos;
	
	public ClienteUDP() throws IOException {
		DatagramSocket socket = null;
		DatagramPacket datosEnv=null;
		
		try{
			socket= new DatagramSocket(CLIPort,InetAddress.getByName(CLIIP));
			System.out.println("conectado socket cliente: "+socket.getLocalAddress());
			
			while(true){
				
				//Lo que el usuario enviará
				
				BufferedReader lector=null;
				lector= new BufferedReader(new InputStreamReader(System.in));
				String mensaje=lector.readLine();//leer la entrada por teclado
				
				if(mensaje.equals("Q")){
					System.out.println("has decidido salir.");
					break;//si metes solo una Q es que quieres salir
				}
				
				String envio= "El cliente dice: "+mensaje;
				datosEnviados=envio.getBytes();
				
				datosEnv= new DatagramPacket(datosEnviados,datosEnviados.length,
												InetAddress.getByName(SRVIP),
												SRVPort);

				
				socket.send(datosEnv);
				System.out.println("enviado el mensaje");
			}
			
		}catch(SocketException | UnknownHostException e){
			e.printStackTrace();
		}
		finally{
			if(socket!=null){
				socket.close();
			}
		}
		
	}
	
	public static void main(String[] args) {
		try {
			new ClienteUDP();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
