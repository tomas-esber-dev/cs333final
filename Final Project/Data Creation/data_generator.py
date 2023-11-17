import json
import random

sentence_templates = [
    "The patient must be {X}, must have {D}, and must live in {L}.",
    "THIS patient must BE AT LEAST {X} years old. Must live IN {L}. They must have {D}.",
    "{X} years old. Lives in {L}. Has {D}",
    "Resides at or near {L}. Is of age, at least {X}. And must be afflicted with {D} condition.",
    "This patient must be experiencing {D}. Must be at or over {X}. Should be within distance of {L}.",
    "THIS PATIENT MUST BE {X} YEARS OLD. THEY MUST LIVE/RESIDE/BE IN PROXIMITY OF {L}. HAS {D} DISEASE.",
    "This clinical trial is open. It is currently accepting patients of ages over {X}. The clinical trial is to take 17 days. It will be located in {L}. Patients must currently be experiencing {D}.",
    "This clinical trial will take two weeks at most. It is accepting patients over {X}. Patients must show symptoms and be diagnosed with {D}. Also, patients should reside neear {L}.",
    "Moderna is conducting a clinical trial (CT), that will require participants to be above {X}. Patients should LIVE at {L}. Participants should also have {D} disease.",
    "The clinical trial is 30 days at most. Patients and participants should be over the age of {X}. We are currently accepting participants to join the trial. Patients should be able to commit theit time to the clinical trial for the entirety of the 30 days. Patients should display symptoms, show signs of, and be diagnosed with {D}. The trial is being conducted in the outskirts of {L}",
    "Location is {L}. NOTE: THE PATIENT MUST BE OVER {X} YEARS OLD. Patients can participate remotely, but it is preferred they live near the site. Patients must have {D}.",
    "This trial is currently open for participants over the age of {X}. We will not accept patients below this age. Patients should expect to participate in this trial for 2-3 weeks. Also, patients should display symptoms of {D}. The trial is being held virtually and on-site. The on-site location for this trial is {L}. Please note, that during the trial you are forbidden to eat fruits such as apples.",
    "We are looking for patients with {D} disease, and this disease must be verified by a licensed MD. Patients must live near {L}, and should be over the age of {X}",
    "Patient must be {X}. Must Have {D}. Must live in {L}.",
    "Pfizer is conducting a clinical trial (CT) which is inviting participants to sign up for. We are studying The Effects that Nebulase OX-C has on {D}. We require that patients are over the minimum age of {X}. We also expect they reside in {L}.",
    "Clinical trials (CTs) are being held in this area. If you live near {L}, here are the following critera to participate: we expect an age of at least {X}, and patients must be afflicted with {D}.",
    "Patients must be {X}, must live in {L}, and must have {D}.",
    "Inclusion: age of {X}, resides at or near {L}, {D} disease (or symptoms whereof).",
    "Inclusion critera: the patient needs to display symptoms of {D}, the patient must live at {L}, the Patient Must have an age of {X}. The trial will take 2 weeks. The trial will be conducted in-person. NOTE: OUT-OF-STATE PATIENTS WILL NOT BE ADMITTED TO THE TRIAL.",
    "THE inclusion criteria of the trial are that the Patient Must Have {D} as a disease and this needs to be certified by a medical practitioner. Patient resides in or at {L}. Patient has a minimum age of {X} years of age",
    "The company of Pfizer is conducting a clinical trial with several eligibility criteria. This is the list of requirements: age: {X}, disease: {D}, location: {L}.",
    "PATIENT must be medically healthy other than having the {D} condition; must be {X} and any gender; must live in {L}; must be willing to sign a consent form for their participation",
    "Patient is any gender and any ethnicity and lives in {L}; patient displays symptoms of {D}; patient is {X} years old.",
    "Clinical trials (CTs) are Now Open! If you live near {L}, you are eligible. We expect an Age of at least {X}, and patients must be afflicted with {D}.",
    "Inclusion Critera for Clinical Trial: Age of {X}; Resides near {L}; has {D} Disease.",
    "The inclusion criteria are that patients have {D} as a disease. The patient (participant) resides in or at {L}. This is mandatory. Patient should have a minimum age of {X} years old.",
    "Critera. Study is for {D} to study effects of rehabilitation through double-blind research. Study is in {L}. Study is for patients over {X}. Please contact 305-893-7744 or ct_nh1@nih.com for more information about the clinical trial.",
    "Medtronic (ID: 3330) is conducting a clinical trial (CT_ID: 3344), that requires participants to be above {X} years of age. Participants should also have {D} disease, and MUST reside at {L} or nearby.",
    "CT Criteria: age of {X}; living at {L}. This study is intended to probe the effects of catalyzing enzymes on early stages of {D}. We expect the study to last several weeks, and expect participants to attend study trials in-person.",
    "Must be experiencing {D}. This trial is in person. Must be at or over {X}. Should live in {L}. Contact: rsrch_nih@gov.com.",
]

lst_diseases = [
    "OCD", 
    "Parkinsons", 
    "Heart Condition", 
    "Lou Gherig's", 
    "ADHD", 
    "COVID", 
    "Alzheimer's", 
    "Dementia", 
    "HIV",
    "Diabetes",
    "Hypertension",
    "Cancer",
    "Influenza",
    "Malaria",
    "Alzheimer's",
    "Arthritis",
    "Asthma",
    "Bronchitis",
    "Celiac Disease",
    "Cholera",
    "Dengue Fever",
    "Epilepsy",
    "Fibromyalgia",
    "Gastritis",
    "Hepatitis",
    "HIV/AIDS",
    "Lupus",
    "Migraine",
    "Multiple Sclerosis",
    "Osteoporosis",
    "Parkinson's Disease",
    "Psoriasis",
    "Schizophrenia",
    "Sickle Cell Anemia",
    "Tuberculosis",
    "Ulcerative Colitis",
    "Yellow Fever",
    "Zika Virus",
    "Anorexia Nervosa",
    "Bulimia Nervosa",
    "Lyme Disease",
    "Hemophilia",
    "Leukemia",
    "Melanoma",
    "Pneumonia",
    "Rheumatoid Arthritis",
    "Tourette Syndrome",
    "Chronic Fatigue Syndrome",
    "Endometriosis",
    "Gout",
    "Hemorrhoids",
    "Huntington's Disease",
    "Obesity",
    "Polycystic Ovary Syndrome (PCOS)",
    "Sleep Apnea",
    "Vitamin D Deficiency",
    "Wilson's Disease",
    "Eczema",
    "Interstitial Cystitis",
    "Crohn's Disease",
    "Irritable Bowel Syndrome (IBS)",
    "Chronic Kidney Disease",
    "Chronic Obstructive Pulmonary Disease (COPD)",
    "Scleroderma",
    "Pulmonary Hypertension",
    "Pulmonary Fibrosis",
    "Restless Legs Syndrome",
    "Tinnitus",
    "Thyroid Disorders",
    "Tourette Syndrome",
    "Trichotillomania",
    "Turner Syndrome",
    "Vasculitis",
    "Vitiligo",
    "West Nile Virus",
    "Yellow Nail Syndrome",
    "Zollinger-Ellison Syndrome",
    "Ehlers-Danlos Syndrome",
    "Guillain-Barre Syndrome",
    "Hirschsprung's Disease",
    "Kawasaki Disease",
    "Lou Gehrig's Disease (ALS)",
    "Macular Degeneration",
    "Myasthenia Gravis",
    "Neurofibromatosis",
    "Pancreatitis",
    "Pulmonary Embolism",
    "Reye's Syndrome",
    "Sjogren's Syndrome",
    "Tay-Sachs Disease",
    "Thalassemia",
    "Von Willebrand Disease",
    "Wegener's Granulomatosis",
    "Xeroderma Pigmentosum",
    "Zollinger-Ellison Syndrome",
    "SARS-CoV-2"
]

states_lst = [ 'AK', 'AL', 'AR', 'AZ', 'CA', 'CO', 'CT', 'DC', 'DE', 'FL', 'GA',
           'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY', 'LA', 'MA', 'MD', 'ME',
           'MI', 'MN', 'MO', 'MS', 'MT', 'NC', 'ND', 'NE', 'NH', 'NJ', 'NM',
           'NV', 'NY', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX',
           'UT', 'VA', 'VT', 'WA', 'WI', 'WV', 'WY']
states_dict = {
    'AK': 'Alaska',
    'AL': 'Alabama',
    'AR': 'Arkansas',
    'AZ': 'Arizona',
    'CA': 'California',
    'CO': 'Colorado',
    'CT': 'Connecticut',
    'DC': 'District of Columbia',
    'DE': 'Delaware',
    'FL': 'Florida',
    'GA': 'Georgia',
    'HI': 'Hawaii',
    'IA': 'Iowa',
    'ID': 'Idaho',
    'IL': 'Illinois',
    'IN': 'Indiana',
    'KS': 'Kansas',
    'KY': 'Kentucky',
    'LA': 'Louisiana',
    'MA': 'Massachusetts',
    'MD': 'Maryland',
    'ME': 'Maine',
    'MI': 'Michigan',
    'MN': 'Minnesota',
    'MO': 'Missouri',
    'MS': 'Mississippi',
    'MT': 'Montana',
    'NC': 'North Carolina',
    'ND': 'North Dakota',
    'NE': 'Nebraska',
    'NH': 'New Hampshire',
    'NJ': 'New Jersey',
    'NM': 'New Mexico',
    'NV': 'Nevada',
    'NY': 'New York',
    'OH': 'Ohio',
    'OK': 'Oklahoma',
    'OR': 'Oregon',
    'PA': 'Pennsylvania',
    'RI': 'Rhode Island',
    'SC': 'South Carolina',
    'SD': 'South Dakota',
    'TN': 'Tennessee',
    'TX': 'Texas',
    'UT': 'Utah',
    'VA': 'Virginia',
    'VT': 'Vermont',
    'WA': 'Washington',
    'WI': 'Wisconsin',
    'WV': 'West Virginia',
    'WY': 'Wyoming'
}

lst_stops = {"{X}": "", "{L}": "", "{D}": ""}

key_to_label = {"{X}": "AGE", "{L}": "LOCATION", "{D}": "DISEASE"}

full_json_page = {"data": []}

for i in range(1, 3000):

    #sentence
    range_sentences = random.randint(0, len(sentence_templates) - 1)
    sentence = sentence_templates[range_sentences]

    #age
    random_age = random.randint(10,99)
    lst_stops["{X}"] = random_age

    #location
    range_states = random.randint(0, 49)
    location = states_dict[states_lst[range_states]]
    lst_stops["{L}"] = location

    #disease
    range_diseases = random.randint(0, len(lst_diseases) - 1)
    disease = lst_diseases[range_diseases]
    lst_stops["{D}"] = disease

    formatted_sentence = sentence.replace("{X}", str(random_age))
    formatted_sentence = formatted_sentence.replace("{L}", location)
    formatted_sentence = formatted_sentence.replace("{D}", disease)

    json_entities = {'entities': []}
    for key in lst_stops:
        # if formatted_sentence.find(str(lst_stops[key])) == -1:
        #     print(sentence)
        start = formatted_sentence.index(str(lst_stops[key]))
        stop = start + len(str(lst_stops[key]))
        json_struct = {"text" : formatted_sentence, "indexes" : str(start)+","+str(stop), "label" : key_to_label[key]}
        json_entities['entities'].append(json_struct)

    full_json_page["data"].append(json_entities)

with open("data.json", "w") as outfile:
    outfile.write(json.dumps(full_json_page, indent=4))
    print("Wrote successfully.")






