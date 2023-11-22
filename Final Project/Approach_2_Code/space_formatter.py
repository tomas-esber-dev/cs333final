s = "   hi . .  ee..."

data = []

data.append({"text": s, "annotations": []})

import re

def trim_entity_spans(data: list) -> list:
    """Removes leading and trailing white spaces from entity spans.

    Args:
        data (list): The data to be cleaned in spaCy JSON format.

    Returns:
        list: The cleaned data.
    """
    invalid_span_tokens = re.compile(r'\.')

    cleaned_data = []
    for entry in data:
        text = entry["text"]
        annotations_list = entry["annotations"]

        valid_entities = []
        for info in annotations_list:
            valid_start = int(info["start"])
            valid_end = int(info["end"])
            if valid_start == 0:
              while valid_start < len(text) and invalid_span_tokens.match(text[valid_start]):
                valid_start += 1
            if valid_end == len(text) - 1:
              while valid_end > 1 and invalid_span_tokens.match(text[valid_end - 1]):
                valid_end -= 1
            valid_entities.append([valid_start, valid_end, label])
        cleaned_data.append([text.strip(), {'entities': valid_entities}])
    return cleaned_data

new_formatted_data = trim_entity_spans(data)
print(new_formatted_data)