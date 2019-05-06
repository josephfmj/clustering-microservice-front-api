package co.edu.ucatolica.clustering.front.api.constant;

public enum ClusteringMethodsConstants {
	
	CLUSTER_RESULT_DEFAULT_COLUMN_NAMES("Nombre_Cluster, Valor"),
	
	KMEANS_ZIP_FILE_NAME("kmeans_result.zip"),
	
	KMEANS_CENTERS_CSV_FILE_NAME("kmeans_centers.csv"),
	
	KMEANS_CLUSTERS_CSV_FILE_NAME("kmeans_clusters.csv"),
	
	AGNES_ZIP_FILE_NAME("agnes_result.zip"),
	
	AGNES_CLUSTERS_CSV_FILE_NAME("agnes_clusters.csv"),
	
	DIANA_ZIP_FILE_NAME("diana_result.zip"),
	
	DIANA_CLUSTERS_CSV_FILE_NAME("diana_clusters.csv"),
	
	PAM_ZIP_FILE_NAME("pam_result.zip"),
	
	PAM_CLUSTERS_CSV_FILE_NAME("pam_clusters.csv"),
	
	PAM_MEDOIDS_CSV_FILE_NAME("pam_medoids.csv"),
	
	PAM_MEDOIDS_COLUMN_NAME("Medoid"),
	
	CLARA_ZIP_FILE_NAME("clara_result.zip"),
	
	CLARA_CLUSTERS_CSV_FILE_NAME("clara_clusters.csv"),
	
	CLARA_MEDOIDS_CSV_FILE_NAME("clara_medoids.csv");
	
	private String value;
	
	private ClusteringMethodsConstants(String value){
		
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
