public static String generateNewStaffId() {
        String lastId = "1000"; // Default start if file is empty

        try (Scanner scanner = new Scanner(new File("staff.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] split = line.split("\\|");
                if (split.length > 0) {
                    lastId = split[0]; // Extract the staff ID (first field)
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading staff file.");
            e.printStackTrace();
        }

        int newId = Integer.parseInt(lastId) + 1; // Increment the latest ID by 1
        return String.valueOf(newId);
    }
