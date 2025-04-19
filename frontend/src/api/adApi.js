
export const fetchAds = async () => {
    try {
        const response = await fetch('http://localhost:5001/ads');
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return await response.json();
    } catch (error) {
        throw new Error(error.message);
    }
};
