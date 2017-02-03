package fr.epita.iam.problem;

public class DeleteException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String deleteFault;
	
	/**
	 * @param message
	 */
	public DeleteException(String message) {
		this.deleteFault = message;
	}

	/**
	 * @return
	 */
	public String getDeleteFault() {
		return deleteFault;
	}

}