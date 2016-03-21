/**
 * The contents of this file are subject to the license and copyright
 * detailed in the LICENSE file at the root of the source
 * tree and available online at
 *
 * https://github.com/keeps/roda
 */
package org.roda.core.data.v2.user;

import java.util.Set;

import org.roda.core.data.v2.index.IsIndexed;

public interface RODAMember extends IsIndexed {

  public boolean isActive();

  public boolean isUser();

  public String getId();

  public String getName();

  public String getFullName();

  public Set<String> getAllGroups();

  public Set<String> getDirectGroups();

  public Set<String> getAllRoles();

  public Set<String> getDirectRoles();

  public boolean isNameValid();

}
