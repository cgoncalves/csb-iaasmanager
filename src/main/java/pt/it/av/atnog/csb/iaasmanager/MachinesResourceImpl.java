package pt.it.av.atnog.csb.iaasmanager;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.ws.rs.core.MediaType;

import org.ow2.sirocco.cimi.domain.CimiMachine;
import org.ow2.sirocco.cimi.domain.collection.CimiMachineCollection;
import org.ow2.sirocco.cimi.sdk.CimiClient;
import org.ow2.sirocco.cimi.sdk.CimiClient.CimiResult;
import org.ow2.sirocco.cimi.sdk.CimiClient.Options;
import org.ow2.sirocco.cimi.sdk.CimiClientException;
import org.ow2.sirocco.cimi.sdk.CimiProviderException;
import org.ow2.sirocco.cimi.sdk.Machine;
import org.ow2.sirocco.cimi.sdk.MachineCreate;
import org.ow2.sirocco.cimi.sdk.MachineTemplate;

@Stateless
public class MachinesResourceImpl implements MachinesResource {
	
	public final int DEFAULT_POLL_PERIOD_IN_SECONDS = 10;
	private static final String IA_URL;
	private static final String IA_USERNAME;
	private static final String IA_PASSWORD;
	private static final String IA_IMAGE_HREF;
	private static final String IA_PROFILE_HREF;
	
	static {
		Properties props = new Properties();
		try {
			props.load(MachinesResourceImpl.class.getResourceAsStream("/iaas_adapter.properties"));
		} catch (Exception ex) {
			throw new RuntimeException("ppm.properties resource is not available");
		}

		IA_URL = props.getProperty("iaas_adapter.url");
		IA_USERNAME = props.getProperty("iaas_adapter.username");
		IA_PASSWORD = props.getProperty("iaas_adapter.password");
		IA_IMAGE_HREF = props.getProperty("iaas_adapter.image_href");
		IA_PROFILE_HREF = props.getProperty("iaas_adapter.profile_href");
	}
	
	private CimiClient client;

	@PostConstruct
	public void init() throws CimiClientException {
		client = CimiClient.login(IA_URL, IA_USERNAME, IA_PASSWORD, Options.build().setDebug(true).setMediaType(MediaType.APPLICATION_XML_TYPE));
	}
	
	@Override
	public CimiMachineCollection getMachines() {
		try {
			return Machine.getMachinesRaw(client);
		} catch (CimiProviderException e) {
			e.printStackTrace();
		} catch (CimiClientException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public CimiMachine getMachine(String id) {
		try {
			return Machine.getMachineByReferenceRaw(client, "machines/"+id); // FIXME
		} catch (CimiProviderException e) {
			e.printStackTrace();
		} catch (CimiClientException e) {
			e.printStackTrace();
		}
		
		return null;
	}

    @Override
    public CimiMachine createMachine() {
        try {
            CimiResult<CimiMachine> result = Machine.createMachineRaw(client, getMachineCreate());
            return result.getResource();
        } catch (CimiProviderException e) {
            e.printStackTrace();
        } catch (CimiClientException e) {
            e.printStackTrace();
        }

        return null;
    }
	
    private MachineCreate getMachineCreate() {
        MachineTemplate machineTemplate = new MachineTemplate();
        machineTemplate.setMachineConfigRef(IA_PROFILE_HREF);
        machineTemplate.setMachineImageRef(IA_IMAGE_HREF);

        MachineCreate machineCreate = new MachineCreate();
        machineCreate.setMachineTemplate(machineTemplate);
        return machineCreate;
    }

//    private CimiMachine waitForMachineRunning(CimiMachine machine) throws TimeoutException, InterruptedException {
//        TimeUnit unit = TimeUnit.SECONDS;
//        long timeout = 300L;
//        long endTime = java.lang.System.nanoTime() + unit.toNanos(timeout);
//        long period = unit.convert(this.DEFAULT_POLL_PERIOD_IN_SECONDS, TimeUnit.SECONDS);
//        long periodInMilliseconds = TimeUnit.MILLISECONDS.convert(period, unit);
//        CimiMachine m;
//
//        try {
//            while (true) {
//                m = Machine.getMachineByReferenceRaw(client, machine.getId());
//                System.out.println("Machine state: " + m.getState());
//                if (m.getState().equals(STATE_STARTED)) {
//                    return m;
//                }
//
//                if (java.lang.System.nanoTime() > endTime) {
//                    throw new TimeoutException();
//                }
//                Thread.sleep(periodInMilliseconds);
//            }
//        } catch (CimiProviderException e) {
//            e.printStackTrace();
//        } catch (CimiClientException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
}
