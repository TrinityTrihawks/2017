package prototypes;

import org.usb4java.Context;
import org.usb4java.Device;
import org.usb4java.DeviceHandle;
import org.usb4java.LibUsb;
import org.usb4java.LibUsbException;

public class Manipulate_Device {
	public static void main(String[] args){
		// Create the libusb context
		Context context = new Context();

		// Initialize the libusb context
		int result = LibUsb.init(context);
		if (result < 0)
		{
			throw new LibUsbException("Unable to initialize libusb", result);
		}
		
		short vendor = 1165;
		short product = 4660;
		DeviceHandle handle = LibUsb.openDeviceWithVidPid(context,  vendor, product);
		Device device = LibUsb.getDevice(handle);
		int result2 = LibUsb.open(device, handle);
		if (result2 != LibUsb.SUCCESS) throw new LibUsbException("Unable to open USB device", result2);
		try
		{
			// Use device handle here
		}
		finally
		{
			LibUsb.close(handle);
		}
	}
}
