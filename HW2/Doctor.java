class Doctor {
    private Nurse nurse;   

   
    public Doctor(Nurse nurse) {
        this.nurse = nurse;
    }

    // Метод, при котором Doctor взаимодействует с Nurse
    public void interactWithNurse() {
        nurse.performTask();  
    }
}
