package main.java.org.usfirst.frc.team4215.robot.steamworks;

import java.util.List;

import javax.usb.UsbConfiguration;
import javax.usb.UsbDevice;
import javax.usb.UsbDeviceDescriptor;
import javax.usb.UsbDisconnectedException;
import javax.usb.UsbEndpoint;
import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbHub;
import javax.usb.UsbInterface;
import javax.usb.UsbNotActiveException;
import javax.usb.UsbNotOpenException;
import javax.usb.UsbPipe;

import org.usb4java.Context;
import org.usb4java.Device;
import org.usb4java.DeviceHandle;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

public class Ultrasonic {
	
    
    static short VENDOR_ID = 0x048d;
    static short PRODUCT_ID = 0x1234;
    
    
    
    public static UsbDevice findUltrasonic(UsbHub hub)
    {
        /*UsbDevice ultra = null;*/
    	UsbDevice ultra;

        for (UsbDevice device: (List<UsbDevice>) hub.getAttachedUsbDevices())
        {
            if (device.isUsbHub())
            {
                ultra = findUltrasonic((UsbHub) device);
                if (ultra != null) return ultra;
            }
            else
            {
                UsbDeviceDescriptor desc = device.getUsbDeviceDescriptor();
                if (desc.idVendor() == VENDOR_ID &&
                    desc.idProduct() == PRODUCT_ID) return device;
            }
        }
        return null;
    }
    
    public static void main(String[] args) throws UsbNotActiveException, UsbNotOpenException, IllegalArgumentException, UsbDisconnectedException, UsbException{
    
    	UsbDevice device = findUltrasonic(UsbHostManager.getUsbServices().getRootUsbHub());
        	if (device == null)
        	{
        		System.err.println("Missile launcher not found.");
        		System.exit(1);
        		return;
        	}
        	UsbConfiguration configuration = device.getActiveUsbConfiguration();
        	UsbInterface iface = configuration.getUsbInterface((byte) 1);
        	iface.claim();
        	try
        	{
        		UsbEndpoint endpoint = iface.getUsbEndpoint((byte) 0x81);
        		UsbPipe pipe = endpoint.getUsbPipe();
        		pipe.open();
        		try
        		{
        			byte[] data = new byte[8];
        			int received = pipe.syncSubmit(data);
        			System.out.println(received + " bytes received");
        		}
        		finally
        		{
        			pipe.close();
        		}
        	}
        	finally        
        	{
        		iface.release();
        	}
}
}