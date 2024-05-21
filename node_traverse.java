public static List<String> getFieldValues(JsonNode node, String fieldPath) {
        List<String> values = new ArrayList<>();
        String[] fields = fieldPath.split("\\.");

        traverseJsonNode(node, fields, 0, values);
        return values;
    }

    private static void traverseJsonNode(JsonNode node, String[] fields, int index, List<String> values) {
        if (index >= fields.length) {
            if (node.isValueNode()) {
                values.add(node.asText());
            }
            return;
        }

        String currentField = fields[index];
        JsonNode nextNode = node.get(currentField);

        if (nextNode == null) {
            return; // Field not found, nothing to do
        }

        if (nextNode.isArray()) {
            for (JsonNode arrayElement : nextNode) {
                traverseJsonNode(arrayElement, fields, index + 1, values);
            }
        } else {
            traverseJsonNode(nextNode, fields, index + 1, values);
        }
    }
}
