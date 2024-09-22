private static void deleteAccessory() {
    System.out.print("Enter Accessory ID: ");
    String id = scanner.nextLine();
    Accessory accessory = accessories.get(id);

    if (accessory != null) {
        accessories.remove(id);
        saveAccessories();
        System.out.println("Accessory with ID " + id + " has been deleted.");
    } else {
        System.out.println("Accessory ID not found.");
    }
}