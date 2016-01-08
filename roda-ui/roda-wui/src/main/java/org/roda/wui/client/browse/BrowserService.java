/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE file at the root of the source
 * tree and available online at
 *
 * https://github.com/keeps/roda
 */
package org.roda.wui.client.browse;

import java.util.List;
import java.util.Map;

import org.roda.core.data.DescriptionObject;
import org.roda.core.data.PluginInfo;
import org.roda.core.data.adapter.facet.Facets;
import org.roda.core.data.adapter.filter.Filter;
import org.roda.core.data.adapter.sort.Sorter;
import org.roda.core.data.adapter.sublist.Sublist;
import org.roda.core.data.common.RODAException;
import org.roda.core.data.exceptions.AlreadyExistsException;
import org.roda.core.data.exceptions.AuthorizationDeniedException;
import org.roda.core.data.exceptions.GenericException;
import org.roda.core.data.exceptions.NotFoundException;
import org.roda.core.data.exceptions.RequestNotValidException;
import org.roda.core.data.v2.IndexResult;
import org.roda.core.data.v2.Job;
import org.roda.core.data.v2.JobReport;
import org.roda.core.data.v2.PluginType;
import org.roda.core.data.v2.RepresentationPreservationObject;
import org.roda.core.data.v2.SimpleDescriptionObject;
import org.roda.core.data.v2.SimpleFile;
import org.roda.core.data.v2.TransferredResource;
import org.roda.wui.client.ingest.process.CreateIngestJobBundle;
import org.roda.wui.client.ingest.process.JobBundle;
import org.roda.wui.client.search.SearchField;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;

/**
 * @author Luis Faria
 * @author Vladislav Korecký <vladislav_korecky@gordic.cz>
 */
public interface BrowserService extends RemoteService {

  /**
   * Service location
   */
  static final String SERVICE_URI = "browserservice";

  /**
   * Utilities
   *
   */
  public static class Util {

    /**
     * Get singleton instance
     *
     * @return the instance
     */
    public static BrowserServiceAsync getInstance() {

      BrowserServiceAsync instance = (BrowserServiceAsync) GWT.create(BrowserService.class);
      ServiceDefTarget target = (ServiceDefTarget) instance;
      target.setServiceEntryPoint(GWT.getModuleBaseURL() + SERVICE_URI);
      return instance;
    }
  }

  /**
   * Get simple descriptive metadata count
   *
   * @param filter
   *
   * @return the number of simple descriptive metadata that fit the filter
   * @throws RequestNotValidException
   * @throws GenericException
   * @throws AuthorizationDeniedException
   */
  Long countDescriptiveMetadata(Filter filter)
    throws AuthorizationDeniedException, GenericException, RequestNotValidException;

  /**
   * Get imple descriptive metadata
   *
   *
   * @return
   * @throws RequestNotValidException
   * @throws AuthorizationDeniedException
   * @throws GenericException
   */
  IndexResult<SimpleDescriptionObject> findDescriptiveMetadata(Filter filter, Sorter sorter, Sublist sublist,
    Facets facets, String locale) throws GenericException, AuthorizationDeniedException, RequestNotValidException;

  BrowseItemBundle getItemBundle(String aipId, String localeString)
    throws AuthorizationDeniedException, GenericException, NotFoundException, RequestNotValidException;

  DescriptiveMetadataEditBundle getDescriptiveMetadataEditBundle(String aipId, String descId)
    throws AuthorizationDeniedException, GenericException, RequestNotValidException, NotFoundException;

  /**
   * Get simple description object
   *
   * @param pid
   *          the object id
   * @return {@link SimpleDescriptionObject}
   * @throws NotFoundException
   * @throws GenericException
   * @throws AuthorizationDeniedException
   */
  SimpleDescriptionObject getSimpleDescriptionObject(String pid)
    throws AuthorizationDeniedException, GenericException, NotFoundException;

  /**
   * Get description object
   *
   * @param pid
   *          the object id
   * @return {@link DescriptionObject}
   * @throws RODAException
   */
  DescriptionObject getDescriptionObject(String pid) throws RODAException;

  /**
   * Get the pid of all ancestors of the node.
   *
   * @param pid
   *          the pid of the node
   * @return A array that starts in the fonds of witch this node belongs to, and
   *         ends in the node itself
   * @throws NotFoundException
   * @throws GenericException
   * @throws AuthorizationDeniedException
   */
  List<SimpleDescriptionObject> getAncestors(SimpleDescriptionObject sdo)
    throws AuthorizationDeniedException, GenericException, NotFoundException;

  public Long countDescriptiveMetadataBinaries(String aipId)
    throws AuthorizationDeniedException, NotFoundException, RequestNotValidException, GenericException;

  List<SearchField> getSearchFields(String locale) throws GenericException;

  /**
   * Get the index of a collection
   *
   * @param collectionPID
   *          the collection id
   * @param filter
   * @param sorter
   * @return the index of the collection
   * @throws RODAException
   */
  // Integer getCollectionIndex(String collectionPID, Filter filter,
  // Sorter sorter) throws RODAException;

  /**
   * Get an item index
   *
   * @param parentPID
   *          the parent pid
   * @param childPID
   *          the item pid
   * @param filter
   * @param sorter
   * @return the item index
   * @throws RODAException
   */
  // Integer getItemIndex(String parentPID, String childPID, Filter
  // filter, Sorter sorter) throws RODAException;

  /**
   * get sub elements
   *
   * @param pid
   *          the parent id
   * @param focusOnChild
   *          the pid of the first item to fetch
   * @param count
   *          the maximum number of items to fetch
   * @param filter
   * @param sorter
   * @return the sub elements list
   * @throws RODAException
   */
  // SimpleDescriptionObject[] getSubElements(String pid, String
  // focusOnChild, int count, Filter filter,
  // Sorter sorter) throws RODAException;

  /**
   * Get representations information
   *
   * @param pid
   *          the id of the associated description object
   * @return the list of representation informations
   * @throws RODAException
   */
  List<RepresentationInfo> getRepresentationsInfo(String doPID) throws RODAException;

  /**
   * Get the Representation Preservation Objects associated with a Descriptive
   * Object
   *
   * @param doPID
   *          the Description Object PID
   * @return The list of associated Representation Preservation Objects
   * @throws RODAException
   */
  List<RepresentationPreservationObject> getDOPreservationObjects(String doPID) throws RODAException;

  /**
   * Get the preservation information
   *
   * @param doPID
   *          the PID of the associated description object
   * @return A list of preservations information
   * @throws RODAException
   */
  List<PreservationInfo> getPreservationsInfo(String doPID) throws RODAException;

  /**
   * Get preservation timeline info
   *
   * @param repPIDs
   *          the PIDs of the representations to show
   * @param icons
   *          the icons to use in each representation, by order
   * @param colors
   *          the colors to use in each representation, by order
   * @param locale
   * @return {@link TimelineInfo}
   * @throws RODAException
   */
  TimelineInfo getPreservationTimeline(List<String> repPIDs, List<String> icons, List<String> colors, String locale)
    throws RODAException;

  SimpleDescriptionObject moveInHierarchy(String aipId, String parentId) throws AuthorizationDeniedException,
    GenericException, NotFoundException, RequestNotValidException, AlreadyExistsException;

  String createAIP(String parentId) throws AuthorizationDeniedException, GenericException, NotFoundException,
    RequestNotValidException, AlreadyExistsException;

  void removeAIP(String aipId)
    throws AuthorizationDeniedException, GenericException, NotFoundException, RequestNotValidException;

  void removeDescriptiveMetadataFile(String itemId, String descriptiveMetadataId)
    throws RODAException, AuthorizationDeniedException, GenericException, NotFoundException, RequestNotValidException;

  void updateDescriptiveMetadataFile(String aipId, DescriptiveMetadataEditBundle bundle)
    throws AuthorizationDeniedException, GenericException, MetadataParseException, NotFoundException,
    RequestNotValidException;

  void createDescriptiveMetadataFile(String aipId, DescriptiveMetadataEditBundle newBundle)
    throws AuthorizationDeniedException, GenericException, MetadataParseException, NotFoundException,
    RequestNotValidException, AlreadyExistsException;

  IndexResult<TransferredResource> findTransferredResources(Filter filter, Sorter sorter, Sublist sublist,
    Facets facets) throws AuthorizationDeniedException, GenericException, RequestNotValidException;

  TransferredResource retrieveTransferredResource(String transferredResourceId)
    throws AuthorizationDeniedException, GenericException, NotFoundException;

  String createTransferredResourcesFolder(String parent, String folderName)
    throws AuthorizationDeniedException, GenericException;

  void removeTransferredResources(List<String> ids)
    throws AuthorizationDeniedException, GenericException, NotFoundException;

  boolean isTransferFullyInitialized() throws AuthorizationDeniedException, GenericException, NotFoundException;

  IndexResult<SimpleFile> getRepresentationFiles(Filter filter, Sorter sorter, Sublist sublist, Facets facets,
    String localeString) throws AuthorizationDeniedException, GenericException, RequestNotValidException;

  IndexResult<Job> findJobs(Filter filter, Sorter sorter, Sublist sublist, Facets facets)
    throws AuthorizationDeniedException, GenericException, RequestNotValidException;

  Job retrieveJob(String jobId) throws AuthorizationDeniedException, GenericException, NotFoundException;

  Job createJob(Job job) throws AuthorizationDeniedException, NotFoundException, RequestNotValidException;

  List<PluginInfo> getPluginsInfo(PluginType type);

  CreateIngestJobBundle getCreateIngestProcessBundle();

  JobBundle retrieveJobBundle(String jobId) throws AuthorizationDeniedException, GenericException, NotFoundException;

  IndexResult<JobReport> findJobReports(Filter filter, Sorter sorter, Sublist sublist, Facets facets)
    throws GenericException, RequestNotValidException;

  List<Viewer> getViewersProperties() throws GenericException;

  Map<String, String> getSupportedMetadata(String locale) throws AuthorizationDeniedException, GenericException;
}
