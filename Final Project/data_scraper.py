import requests
import json

# api_url = 'https://clinicaltrials.gov/api/query/full_studies?expr=heart+attack&aggFilters=results:with,status:com&min_rnk=1&max_rnk=2&fmt=json'

# single study 
# https://clinicaltrials.gov/study/NCT05940077?rank=1&viewType=Table&aggFilters=results:with,status:com&tab=results
api_url = 'https://clinicaltrials.gov/api/v2/studies?aggFilters=results:with,status:com'


params = {
}

response = requests.get(api_url)

if response.status_code == 200:
    print(response.status_code)
else:
    print("Error: ", response.status_code, response.text)

with open("outfile_test.json", "w") as outfile:
    raw_json = response.json()
    json_object = json.dumps(raw_json, indent=4)

    # study_data = {}

    # study_data['identificationModule'] = raw_json['studies'][0]['protocolSection']['identificationModule']
    
    # study_data['eligibilityModule'] = raw_json['studies'][0]['protocolSection']['eligibilityModule']

    # all_data = json.dumps(study_data, indent=4)

    # outfile.write(all_data)

    outfile.write(json_object) # (testing)

    print("Wrote successfully")

