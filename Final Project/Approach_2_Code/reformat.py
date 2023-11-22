import json
import os

with open("conso_outfile_new.json", "r") as file:
    data = json.load(file)

json_lst = []

for trial_id in data:
    json_lst_item = {}
    lst_anno = []
    if "Annotations" in data[trial_id] and "Text" in data[trial_id]:
        if data[trial_id]["Annotations"] != [] and len(data[trial_id]["Text"]) > 0:
            for line in data[trial_id]["Annotations"]:
                line = line.split(' ')
                label = line[0]
                start = line[1]
                end = line[2]
                lst_anno.append({"start": start, "end": end, "label": label})
            json_lst_item["annotations"] = lst_anno
            json_lst_item["text"] = data[trial_id]["Text"]
            json_lst.append(json_lst_item)

with open("reformatted.json", "w") as writer:
    json.dump(json_lst, writer, indent=4)

        
