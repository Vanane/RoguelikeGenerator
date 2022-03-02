package com.pitchounous;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DescriptorCategory {
    Class<?> baseClass;
    List<PluginDescriptor> descriptors;
    Set<PluginDescriptor> selectedDescriptors;
    boolean atLeastOneRequired;

    /**
     * Simple Category to regroup all plugin from the same base class
     * 
     * @param descriptors
     * @param atLeastOneRequired
     */
    public DescriptorCategory(Class<?> baseClass, List<PluginDescriptor> descriptors, boolean atLeastOneRequired) {
        this.baseClass = baseClass;
        this.descriptors = descriptors;
        this.atLeastOneRequired = atLeastOneRequired;
        this.selectedDescriptors = new HashSet<>();
    }

    /**
     * Allow selection of some plugins
     * 
     * @param pd
     */
    public void selectPluginDescriptor(PluginDescriptor pd) {
        if (!descriptors.contains(pd)) {
            System.err.println("You tried to add " + pd.getFullClassPath() + " to the wrong category");
        } else {
            selectedDescriptors.add(pd);
        }
    }

    /**
     * Allow to unselect some plugins
     * 
     * @param pd
     */
    public void unselectPluginDescriptor(PluginDescriptor pd) {
        if (!selectedDescriptors.contains(pd)) {
            System.err.println("You tried to delete " + pd.getFullClassPath() + " while it has not been selected");
        } else {
            selectedDescriptors.remove(pd);
        }
    }

    /**
     * 
     * @return
     */
    public Set<PluginDescriptor> getSelectedDescriptors() {
        return selectedDescriptors;
    }
}
