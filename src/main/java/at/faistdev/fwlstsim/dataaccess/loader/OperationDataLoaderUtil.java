package at.faistdev.fwlstsim.dataaccess.loader;

import at.faistdev.fwlstsim.dataaccess.cache.OperationKeywordCache;
import at.faistdev.fwlstsim.dataaccess.entities.OperationKeyword;
import at.faistdev.fwlstsim.dataaccess.entities.OperationResource;
import at.faistdev.fwlstsim.dataaccess.entities.PossibleOperation;
import at.faistdev.fwlstsim.dataaccess.loader.operations.PossibleOperationResponse;
import at.faistdev.fwlstsim.dataaccess.loader.operations.PossibleOperationsResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OperationDataLoaderUtil {

    public static ArrayList<PossibleOperation> getOperations(URI fileUri) {
        ArrayList<PossibleOperation> operations = new ArrayList<>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(fileUri);
            if (file.exists() == false) {
                System.err.println("File does not exist");
                return operations;
            }

            PossibleOperationsResponse response = mapper.readValue(file, PossibleOperationsResponse.class);

            for (PossibleOperationResponse operation : response.operations) {
                OperationKeyword keyword = OperationKeywordCache.getCache().getByName(operation.type);
                if (keyword == null) {
                    throw new Exception("Keyword " + operation.type + " does not exist");
                }

                Set<OperationResource> resources = getResources(operation.requiredResources);

                PossibleOperation possibleOperation = new PossibleOperation(keyword, operation.callingNumber, operation.callText, operation.initialRadioMessage, resources);
                operations.add(possibleOperation);
            }

        } catch (IOException ex) {
            System.getLogger(AddressDataLoaderUtil.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        } catch (Exception e) {
            System.err.println(e);
        }

        return operations;
    }

    private static Set<OperationResource> getResources(List<String> resourcesAsString) throws Exception {
        HashSet<OperationResource> resources = new HashSet<>();
        for (String resString : resourcesAsString) {
            OperationResource resource = OperationResource.valueOf(resString);
            if (resource == null) {
                throw new Exception("Resource " + resString + " does not exist");
            }

            resources.add(resource);
        }

        return resources;
    }
}
