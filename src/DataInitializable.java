
/**
     * used in scenemanager to (hopefully) switch scenes while maintaining data
     * with any luck this might address the problem with controllers forgetting who they are after every scene
     * @param <T> the type of data that will be passed to the controller
     */
    public interface DataInitializable<T> {
        void initializeData(T data);
    }
